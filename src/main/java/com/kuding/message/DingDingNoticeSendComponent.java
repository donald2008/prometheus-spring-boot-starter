package com.kuding.message;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.content.ExceptionNotice;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeProperty;

public class DingDingNoticeSendComponent implements INoticeSendComponent {

	private final DingdingHttpClient httpClient;

	private final ExceptionNoticeProperty exceptionNoticeProperty;

	private Map<String, DingDingExceptionNoticeProperty> map;

	private final Log logger = LogFactory.getLog(getClass());

	public DingDingNoticeSendComponent(DingdingHttpClient httpClient, ExceptionNoticeProperty exceptionNoticeProperty,
			Map<String, DingDingExceptionNoticeProperty> map) {
		this.httpClient = httpClient;
		this.exceptionNoticeProperty = exceptionNoticeProperty;
		this.map = map;
	}

	/**
	 * @return the exceptionNoticeProperty
	 */
	public ExceptionNoticeProperty getExceptionNoticeProperty() {
		return exceptionNoticeProperty;
	}

	/**
	 * @return the map
	 */
	public Map<String, DingDingExceptionNoticeProperty> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, DingDingExceptionNoticeProperty> map) {
		this.map = map;
	}

	@Override
	public void send(String blamedFor, ExceptionNotice exceptionNotice) {
		DingDingExceptionNoticeProperty dingDingExceptionNoticeProperty = map.get(blamedFor);
		if (dingDingExceptionNoticeProperty != null) {
			DingDingNotice dingDingNotice = new DingDingNotice(exceptionNotice.createText(),
					dingDingExceptionNoticeProperty.getPhoneNum());
			DingDingResult result = httpClient.post(dingDingExceptionNoticeProperty.getWebHook(), dingDingNotice,
					DingDingResult.class);
			logger.debug(result);
		} else
			logger.error("无法进行钉钉通知，不存在背锅侠");
	}

	@Override
	public Collection<String> getAllBuddies() {
		return map.keySet();
	}

}
