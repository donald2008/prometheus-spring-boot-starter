package com.kuding.config;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.markdown.DefaultMarkdownHttpMessageResolver;
import com.kuding.markdown.DefaultMarkdownMessageResolver;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.properties.enums.ListenType;
import com.kuding.text.ExceptionNoticeResolver;
import com.kuding.text.ExceptionNoticeResolverFactory;

@Configuration
@ConditionalOnBean({ ExceptionHandler.class })
public class ExceptionNoticeDingdingSendingConfig implements ExceptionSendComponentConfigure {

	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@Autowired
	private DingdingHttpClient dingdingHttpClient;

	@Autowired
	private ExceptionNoticeResolverFactory exceptionNoticeResolverFactory;

	private final Log logger = LogFactory.getLog(getClass());

	public ExceptionNoticeDingdingSendingConfig() {
		logger.debug("------------加载ExceptionNoticeDingdingSendingConfig");
	}

	@Override
	public void addSendComponent(ExceptionHandler exceptionHandler) {
		logger.debug("注册钉钉通知");
		Map<String, DingDingExceptionNoticeProperty> map = exceptionNoticeProperty.getDingding();
		DingDingNoticeSendComponent component = new DingDingNoticeSendComponent(dingdingHttpClient,
				exceptionNoticeProperty, map, exceptionNoticeResolverFactory);
		exceptionHandler.registerNoticeSendComponent(component);
	}

	@Override
	public void addMessageResolver(ExceptionNoticeResolverFactory exceptionNoticeResolverFactory) {
		if (exceptionNoticeProperty.getDingdingTextType() == DingdingTextType.MARKDOWN) {
			ExceptionNoticeResolver exceptionNoticeResolver = null;
			if (exceptionNoticeProperty.getListenType() == ListenType.COMMON)
				exceptionNoticeResolver = new DefaultMarkdownMessageResolver(exceptionNoticeProperty);
			if (exceptionNoticeProperty.getListenType() == ListenType.WEB_MVC)
				exceptionNoticeResolver = new DefaultMarkdownHttpMessageResolver(exceptionNoticeProperty);
			exceptionNoticeResolverFactory.addNoticeResolver("dingding", exceptionNoticeResolver);
		}
	}
}
