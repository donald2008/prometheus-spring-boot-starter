package com.kuding.exceptionhandle.event;

import org.springframework.context.ApplicationEvent;

import com.kuding.pojos.ExceptionNotice;

public class ExceptionNoticeEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private final ExceptionNotice exceptionNotice;

	/**
	 * @param source
	 * @param exceptionNotice
	 */
	public ExceptionNoticeEvent(Object source, ExceptionNotice exceptionNotice) {
		super(source);
		this.exceptionNotice = exceptionNotice;
	}

	/**
	 * @return the exceptionNotice
	 */
	public ExceptionNotice getExceptionNotice() {
		return exceptionNotice;
	}

}
