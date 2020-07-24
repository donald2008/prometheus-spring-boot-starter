package com.kuding.message;

import com.kuding.pojos.PromethuesNotice;

@FunctionalInterface
public interface INoticeSendComponent<T extends PromethuesNotice> {

	public void send(T notice);

}
