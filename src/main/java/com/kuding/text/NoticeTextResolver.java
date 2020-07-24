package com.kuding.text;

import com.kuding.pojos.PromethuesNotice;

@FunctionalInterface
public interface NoticeTextResolver<T extends PromethuesNotice> {

	public String resolve(T object);
}
