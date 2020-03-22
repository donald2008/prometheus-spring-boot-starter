package com.kuding.storage;

import com.kuding.content.ExceptionNotice;

@FunctionalInterface
public interface ExceptionNoticeStorage {

	public void saveExcepion(ExceptionNotice exceptionNotice);
}
