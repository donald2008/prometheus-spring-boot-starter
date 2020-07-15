package com.kuding.message;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	private final Log logger = LogFactory.getLog(DingDingNoticeSendComponent.class);

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
			Map<String, Object> map = new HashMap<String, Object>();
			if (dingDingExceptionNoticeProperty.isEnableSignatureCheck()) {
				long timestamp = System.currentTimeMillis();
				map.put("sign",
						generateSign(System.currentTimeMillis(), dingDingExceptionNoticeProperty.getSignSecret()));
				map.put("timestamp", timestamp);
			}
			DingDingResult result = httpClient.post(dingDingExceptionNoticeProperty.getAccessToken(), dingDingNotice,
					map);
			logger.debug(result);
		} else
			logger.error("无法进行钉钉通知，不存在背锅侠");
	}

	protected String generateSign(Long timestamp, String sec) {
		String combine = String.format("%d\n%s", timestamp, sec);
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(sec.getBytes("UTF-8"), "HmacSHA256"));
			byte[] signData = mac.doFinal(combine.getBytes("UTF-8"));
			return Base64.encodeBase64String(signData);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
