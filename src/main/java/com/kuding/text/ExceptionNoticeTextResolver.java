package com.kuding.text;

import com.kuding.content.ExceptionNotice;

@FunctionalInterface
public interface ExceptionNoticeTextResolver {

	public String resolve(ExceptionNotice exceptionNotice);
}
