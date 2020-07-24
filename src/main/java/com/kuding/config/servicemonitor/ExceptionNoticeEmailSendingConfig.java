package com.kuding.config.servicemonitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import com.kuding.config.annos.ConditionalOnServiceMonitor;
import com.kuding.message.EmailNoticeSendComponent;
import com.kuding.message.INoticeSendComponent;
import com.kuding.pojos.servicemonitor.ServiceCheckNotice;
import com.kuding.properties.EmailNoticeProperty;
import com.kuding.text.ServiceMonitorResolver;

@Configuration
@AutoConfigureAfter({ MailSenderAutoConfiguration.class })
@ConditionalOnBean({ MailSender.class, MailProperties.class })
@ConditionalOnServiceMonitor
@ConditionalOnProperty(value = "prometheus.email.enabled", havingValue = "email")
public class ExceptionNoticeEmailSendingConfig {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private MailProperties mailProperties;
	@Autowired
	private EmailNoticeProperty emailExceptionNoticeProperty;

	@Bean
	@ConditionalOnMissingBean(parameterizedContainer = INoticeSendComponent.class)
	public INoticeSendComponent<ServiceCheckNotice> emailNoticeSendComponent(
			ServiceMonitorResolver exceptionNoticeResolver) {
		INoticeSendComponent<ServiceCheckNotice> component = new EmailNoticeSendComponent<ServiceCheckNotice>(
				mailSender, mailProperties, emailExceptionNoticeProperty, exceptionNoticeResolver);
		return component;
	}

	@Bean
	@ConditionalOnMissingBean
	public ServiceMonitorResolver serviceMonitorResolver() {
		return x -> x.generateText();
	}
}
