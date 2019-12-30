package com.kuding.config;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.message.EmailNoticeSendComponent;
import com.kuding.properties.EmailExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeProperty;

@Configuration
@ConditionalOnProperty(name = "exceptionnotice.open-notice", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter({ MailSenderAutoConfiguration.class, ExceptionNoticeConfig.class })
@ConditionalOnBean({ MailSender.class, ExceptionHandler.class })
public class ExceptionNoticeEmailConfig implements ExceptionSendComponentConfigure {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	private final Log logger = LogFactory.getLog(getClass());

	public ExceptionNoticeEmailConfig() {
		logger.debug("------------加载ExceptionNoticeEmailConfig");
	}

	@Override
	public void regist(ExceptionHandler exceptionHandler) {
		Map<String, EmailExceptionNoticeProperty> emails = exceptionNoticeProperty.getEmail();
		if (emails != null && emails.size() > 0) {
			EmailNoticeSendComponent component = new EmailNoticeSendComponent(mailSender, mailProperties, emails);
			exceptionHandler.registerNoticeSendComponent(component);
		}
	}
}
