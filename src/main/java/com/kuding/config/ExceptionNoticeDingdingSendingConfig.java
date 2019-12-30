package com.kuding.config;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.message.DingDingNoticeSendComponent;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeProperty;
import com.kuding.text.ExceptionNoticeResolverFactory;

@Configuration
@AutoConfigureAfter({ ExceptionNoticeConfig.class })
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
	public void regist(ExceptionHandler exceptionHandler) {
		Map<String, DingDingExceptionNoticeProperty> map = exceptionNoticeProperty.getDingding();
		DingDingNoticeSendComponent component = new DingDingNoticeSendComponent(dingdingHttpClient,
				exceptionNoticeProperty, map, exceptionNoticeResolverFactory);
		exceptionHandler.registerNoticeSendComponent(component);
	}

}
