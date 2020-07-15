package com.kuding.httpclient;

import java.util.Map;

import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

@FunctionalInterface
public interface DingdingHttpClient {

	@RequestLine("POST /send?access_token={accessToken}")
	@Headers("Content-Type: application/json; charset=utf-8")
	@Body("{body}")
	DingDingResult post(@Param("accessToken") String accessToken,
			@Param(value = "body", expander = JsonExpander.class) DingDingNotice body,
			@QueryMap Map<String, Object> map);

}
