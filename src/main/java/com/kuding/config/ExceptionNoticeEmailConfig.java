package com.kuding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import com.kuding.message.EmailNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.properties.EmailExceptionNoticeProperty;
import com.kuding.text.ExceptionNoticeResolver;

@Configuration
@AutoConfigureAfter({ MailSenderAutoConfiguration.class })
@ConditionalOnBean({ MailSender.class, MailProperties.class })
@ConditionalOnProperty(name = "exceptionnotice.open-notice", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties({ EmailExceptionNoticeProperty.class })
public class ExceptionNoticeEmailConfig {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private EmailExceptionNoticeProperty emailExceptionNoticeProperty;

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "exceptionnotice.notice-type", havingValue = "email")
	public INoticeSendComponent emailNoticeSendComponent(ExceptionNoticeResolver exceptionNoticeResolver) {
		INoticeSendComponent component = new EmailNoticeSendComponent(mailSender, mailProperties,
				emailExceptionNoticeProperty, exceptionNoticeResolver);
		return component;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(value = "exceptionnotice.notice-type", havingValue = "email")
	public ExceptionNoticeResolver exceptionNoticeResolver() {
		return x -> x.createText();
	}
}
