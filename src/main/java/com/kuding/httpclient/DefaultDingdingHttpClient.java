package com.kuding.httpclient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;

import feign.Feign;
import feign.FeignException;
import feign.Logger.Level;
import feign.Request.Body;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;

public class DefaultDingdingHttpClient implements DingdingHttpClient {

	private final DingdingHttpClient dingdingHttpClient = Feign.builder().encoder(new GsonEncoder())
			.decoder(new GsonDecoder()).logger(new Slf4jLogger()).logLevel(Level.FULL)
			.target(DingdingHttpClient.class, "https://oapi.dingtalk.com/robot");

	private final Gson gson;

	private final Log logger = LogFactory.getLog(getClass());

	public DefaultDingdingHttpClient(Gson gson) {
		this.gson = gson;
	}

	@Override
	public DingDingResult post(String accessToken, DingDingNotice body, Map<String, Object> map) {
		logger.debug("发送钉钉请求:" + body.getText());
		return dingdingHttpClient.post(accessToken, body, map);
	}

	class GsonDecoder implements Decoder {

		@Override
		public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
			return gson.fromJson(response.body().asReader(StandardCharsets.UTF_8), type);
		}

	}

	class GsonEncoder implements Encoder {

		@Override
		public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
			template.body(Body.encoded(gson.toJson(object).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
		}

	}
}
