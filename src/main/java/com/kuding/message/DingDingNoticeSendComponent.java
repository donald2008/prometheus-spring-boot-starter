package com.kuding.message;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.kuding.content.ExceptionNotice;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.pojos.dingding.DingDingAt;
import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.text.ExceptionNoticeTextResolver;

public class DingDingNoticeSendComponent implements INoticeSendComponent {

	private final DingdingHttpClient httpClient;

	private final DingDingExceptionNoticeProperty dingDingExceptionNoticeProperty;

	private final ExceptionNoticeTextResolver exceptionNoticeResolver;

	private final Log logger = LogFactory.getLog(getClass());

	public DingDingNoticeSendComponent(DingdingHttpClient httpClient,
			DingDingExceptionNoticeProperty dingDingExceptionNoticeProperty,
			ExceptionNoticeTextResolver exceptionNoticeResolver) {
		this.httpClient = httpClient;
		this.dingDingExceptionNoticeProperty = dingDingExceptionNoticeProperty;
		this.exceptionNoticeResolver = exceptionNoticeResolver;
	}

	/**
	 * @return the dingDingExceptionNoticeProperty
	 */
	public DingDingExceptionNoticeProperty getDingDingExceptionNoticeProperty() {
		return dingDingExceptionNoticeProperty;
	}

	@Override
	public void send(ExceptionNotice exceptionNotice) {
		if (dingDingExceptionNoticeProperty != null) {
			String notice = exceptionNoticeResolver.resolve(exceptionNotice);
			DingDingNotice dingDingNotice = dingDingExceptionNoticeProperty
					.getDingdingTextType() == DingdingTextType.TEXT
							? new DingDingNotice(notice, new DingDingAt(dingDingExceptionNoticeProperty.getPhoneNum()))
							: new DingDingNotice("异常通知", notice,
									new DingDingAt(dingDingExceptionNoticeProperty.getPhoneNum()));
			DingDingResult result = httpClient.post(generateUrl(dingDingExceptionNoticeProperty), dingDingNotice,
					DingDingResult.class);
			logger.debug(result);
		} else
			logger.error("无法进行钉钉通知，不存在背锅侠");
	}

	protected URI generateUrl(DingDingExceptionNoticeProperty dingDingExceptionNoticeProperty) {
		boolean enableSign = dingDingExceptionNoticeProperty.isEnableSignatureCheck();
		String signSec = dingDingExceptionNoticeProperty.getSignSecret();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(dingDingExceptionNoticeProperty.getWebHook());
		if (enableSign && !StringUtils.isEmpty(signSec)) {
			Long timestamp = System.currentTimeMillis();
			String sign = generateSign(timestamp, signSec);
			Assert.notNull(sign, "calculate sign goes error!");
			builder.queryParam("timestamp", timestamp).queryParam("sign", sign);
		}
		URI uri = builder.build(true).toUri();
		return uri;
	}

	protected String generateSign(Long timestamp, String sec) {
		String combine = String.format("%d\n%s", timestamp, sec);
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(sec.getBytes("UTF-8"), "HmacSHA256"));
			byte[] signData = mac.doFinal(combine.getBytes("UTF-8"));
			return URLEncoder.encode(Base64.encodeBase64String(signData), "UTF-8");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
