package com.kuding.exceptionhandle.event;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.exceptionhandle.interfaces.ExceptionNoticeStatisticsRepository;
import com.kuding.message.INoticeSendComponent;
import com.kuding.pojos.ExceptionNotice;
import com.kuding.properties.exception.ExceptionNoticeFrequencyStrategy;

public class ExceptionNoticeAsyncSendListener extends AbstractNoticeSendListener {

	private static final Log logger = LogFactory.getLog(ExceptionNoticeAsyncSendListener.class);

	private final Executor executor;

	/**
	 * @param exceptionNoticeFrequencyStrategy
	 * @param exceptionNoticeStatisticsRepository
	 * @param noticeSendComponents
	 */
	public ExceptionNoticeAsyncSendListener(ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy,
			ExceptionNoticeStatisticsRepository exceptionNoticeStatisticsRepository,
			List<INoticeSendComponent<ExceptionNotice>> noticeSendComponents, Executor executor) {
		super(exceptionNoticeFrequencyStrategy, exceptionNoticeStatisticsRepository, noticeSendComponents);
		this.executor = executor;
	}

	@Override
	public void onApplicationEvent(ExceptionNoticeEvent event) {
		logger.debug("异步发送消息");
		executor.execute(() -> send(event.getExceptionNotice()));
	}

}
