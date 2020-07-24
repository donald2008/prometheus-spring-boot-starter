package com.kuding.exceptionhandle.event;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.exceptionhandle.interfaces.ExceptionNoticeStatisticsRepository;
import com.kuding.message.INoticeSendComponent;
import com.kuding.pojos.ExceptionNotice;
import com.kuding.properties.exception.ExceptionNoticeFrequencyStrategy;

public class ExceptionNoticeSendListener extends AbstractNoticeSendListener {

	private final static Log logger = LogFactory.getLog(ExceptionNoticeSendListener.class);

	/**
	 * @param exceptionNoticeFrequencyStrategy
	 * @param exceptionNoticeStatisticsRepository
	 * @param noticeSendComponents
	 */
	public ExceptionNoticeSendListener(ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy,
			ExceptionNoticeStatisticsRepository exceptionNoticeStatisticsRepository,
			List<INoticeSendComponent<ExceptionNotice>> noticeSendComponents) {
		super(exceptionNoticeFrequencyStrategy, exceptionNoticeStatisticsRepository, noticeSendComponents);
	}

	@Override
	public void onApplicationEvent(ExceptionNoticeEvent event) {
		logger.debug("消息同步发送");
		send(event.getExceptionNotice());
	}

}
