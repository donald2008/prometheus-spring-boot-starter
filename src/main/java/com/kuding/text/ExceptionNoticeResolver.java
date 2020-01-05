package com.kuding.text;

import com.kuding.content.ExceptionNotice;

@FunctionalInterface
public interface ExceptionNoticeResolver {

	public String resolve(ExceptionNotice exceptionNotice);
}
