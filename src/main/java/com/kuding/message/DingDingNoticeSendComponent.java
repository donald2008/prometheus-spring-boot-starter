package com.kuding.message;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.content.ExceptionNotice;
import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.pojos.dingding.DingDingAt;
import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;
import com.kuding.properties.DingDingExceptionNoticeProperty;
import com.kuding.properties.ExceptionNoticeProperty;
import com.kuding.properties.enums.DingdingTextType;
import com.kuding.text.ExceptionNoticeResolverFactory;

public class DingDingNoticeSendComponent implements INoticeSendComponent {

	private final DingdingHttpClient httpClient;

	private final ExceptionNoticeProperty exceptionNoticeProperty;

	private Map<String, DingDingExceptionNoticeProperty> map;

	private final ExceptionNoticeResolverFactory exceptionNoticeResolverFactory;

	private final Log logger = LogFactory.getLog(getClass());

	public DingDingNoticeSendComponent(DingdingHttpClient httpClient, ExceptionNoticeProperty exceptionNoticeProperty,
			Map<String, DingDingExceptionNoticeProperty> map,
			ExceptionNoticeResolverFactory exceptionNoticeResolverFactory) {
		this.httpClient = httpClient;
		this.exceptionNoticeProperty = exceptionNoticeProperty;
		this.map = map;
		this.exceptionNoticeResolverFactory = exceptionNoticeResolverFactory;
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
			String notice = exceptionNoticeResolverFactory.resolve("dingding", exceptionNotice);
			DingDingNotice dingDingNotice = exceptionNoticeProperty.getDingdingTextType() == DingdingTextType.TEXT
					? new DingDingNotice(notice, new DingDingAt(dingDingExceptionNoticeProperty.getPhoneNum()))
					: new DingDingNotice("异常通知", notice, new DingDingAt(dingDingExceptionNoticeProperty.getPhoneNum()));
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
