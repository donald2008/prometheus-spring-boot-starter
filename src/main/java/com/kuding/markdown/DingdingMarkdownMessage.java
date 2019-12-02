package com.kuding.markdown;

import com.kuding.content.ExceptionNotice;

@FunctionalInterface
public interface DingdingMarkdownMessage {

	public String markdown(ExceptionNotice exceptionNotice);
}
