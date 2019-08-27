package com.kuding.config;

import org.springframework.beans.factory.InitializingBean;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.message.INoticeSendComponent;

public abstract class SendCompoentRegistrator implements InitializingBean {

	public abstract INoticeSendComponent genericSendComponent();

	public abstract ExceptionHandler getExceptionHandler();

	@Override
	public void afterPropertiesSet() throws Exception {
		getExceptionHandler().setSendComponent(genericSendComponent());
	}

}
