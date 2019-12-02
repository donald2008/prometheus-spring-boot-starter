package com.kuding.httpclient;

import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;

@FunctionalInterface
public interface DingdingHttpClient {

	DingDingResult post(String url, DingDingNotice body, Class<DingDingResult> clazz);
}
