package com.kuding.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.kuding.exceptionhandle.ExceptionHandler;

public class ExceptionSendConfigComposite implements ExceptionSendComponentConfigure {

	private List<ExceptionSendComponentConfigure> list = new ArrayList<>();

	@Override
	public void addSendComponent(ExceptionHandler exceptionHandler) {
		for (ExceptionSendComponentConfigure exceptionSendComponentConfigure : list) {
			exceptionSendComponentConfigure.addSendComponent(exceptionHandler);
		}
	}

	public void addAll(Collection<ExceptionSendComponentConfigure> list) {
		this.list.addAll(list);
	}

}
