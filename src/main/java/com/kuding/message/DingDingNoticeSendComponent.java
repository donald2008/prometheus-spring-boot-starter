package com.kuding.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.httpclient.DingdingHttpClient;
import com.kuding.pojos.PromethuesNotice;
import com.kuding.pojos.dingding.DingDingNotice;
import com.kuding.pojos.dingding.DingDingResult;
import com.kuding.properties.DingDingNoticeProperty;
import com.kuding.text.NoticeTextResolver;

public class DingDingNoticeSendComponent<T extends PromethuesNotice> implements INoticeSendComponent<T> {

	private final DingdingHttpClient httpClient;

	private final NoticeTextResolver<T> noticeResolver;

	private final DingDingNoticeProperty dingDingNoticeProperty;

	private final Log logger = LogFactory.getLog(DingDingNoticeSendComponent.class);

	/**
	 * @param httpClient
	 * @param exceptionNoticeResolver
	 * @param dingDingNoticeProperty
	 */
	public DingDingNoticeSendComponent(DingdingHttpClient httpClient, NoticeTextResolver<T> noticeResolver,
			DingDingNoticeProperty dingDingNoticeProperty) {
		this.httpClient = httpClient;
		this.noticeResolver = noticeResolver;
		this.dingDingNoticeProperty = dingDingNoticeProperty;
	}

	/**
	 * @return the httpClient
	 */
	public DingdingHttpClient getHttpClient() {
		return httpClient;
	}

	@Override
	public void send(T exceptionNotice) {
		String noticeText = noticeResolver.resolve(exceptionNotice);
		DingDingNotice dingDingNotice = dingDingNoticeProperty.generateDingdingNotice(noticeText,
				exceptionNotice.getTitle());
		DingDingResult result = httpClient.doSend(dingDingNotice);
		logger.debug(result);
	}

}
