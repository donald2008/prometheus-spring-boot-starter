package com.kuding.web;

import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

public interface CurrentRequetBodyResolver extends RequestBodyAdvice {

	default String getRequestBody() {
		return "";
	}
}
