package com.kuding.config;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.text.ExceptionNoticeResolverFactory;

public interface ExceptionSendComponentConfigure {

	default public void addSendComponent(ExceptionHandler exceptionHandler) {

	}

	default public void addMessageResolver(ExceptionNoticeResolverFactory exceptionNoticeResolverFactory) {

	}
}
