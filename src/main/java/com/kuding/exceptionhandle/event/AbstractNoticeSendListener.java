package com.kuding.exceptionhandle.event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationListener;

import com.kuding.exceptionhandle.interfaces.ExceptionNoticeStatisticsRepository;
import com.kuding.message.INoticeSendComponent;
import com.kuding.pojos.ExceptionNotice;
import com.kuding.pojos.ExceptionStatistics;
import com.kuding.properties.exception.ExceptionNoticeFrequencyStrategy;

public abstract class AbstractNoticeSendListener implements ApplicationListener<ExceptionNoticeEvent> {

	private final ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy;

	private final ExceptionNoticeStatisticsRepository exceptionNoticeStatisticsRepository;

	private final List<INoticeSendComponent<ExceptionNotice>> noticeSendComponents;

	/**
	 * @param exceptionNoticeFrequencyStrategy
	 * @param exceptionNoticeStatisticsRepository
	 * @param noticeSendComponents
	 */
	public AbstractNoticeSendListener(ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy,
			ExceptionNoticeStatisticsRepository exceptionNoticeStatisticsRepository,
			List<INoticeSendComponent<ExceptionNotice>> noticeSendComponents) {
		this.exceptionNoticeFrequencyStrategy = exceptionNoticeFrequencyStrategy;
		this.exceptionNoticeStatisticsRepository = exceptionNoticeStatisticsRepository;
		this.noticeSendComponents = noticeSendComponents;
	}

	/**
	 * @return the exceptionNoticeFrequencyStrategy
	 */
	public ExceptionNoticeFrequencyStrategy getExceptionNoticeFrequencyStrategy() {
		return exceptionNoticeFrequencyStrategy;
	}

	/**
	 * @return the exceptionNoticeStatisticsRepository
	 */
	public ExceptionNoticeStatisticsRepository getExceptionNoticeStatisticsRepository() {
		return exceptionNoticeStatisticsRepository;
	}

	public void send(ExceptionNotice notice) {
		ExceptionStatistics statistics = exceptionNoticeStatisticsRepository.increaseOne(notice);
		if (stratergyCheck(statistics, exceptionNoticeFrequencyStrategy)) {
			notice.setShowCount(statistics.getShowCount().longValue());
			notice.setCreateTime(LocalDateTime.now());
			noticeSendComponents.forEach(x -> x.send(notice));
			exceptionNoticeStatisticsRepository.increaseShowOne(statistics);
		}
	}

	protected boolean stratergyCheck(ExceptionStatistics exceptionStatistics,
			ExceptionNoticeFrequencyStrategy exceptionNoticeFrequencyStrategy) {
		if (exceptionStatistics.isFirstCreated()) {
			exceptionStatistics.setFirstCreated(false);
			return true;
		}
		boolean flag = false;
		switch (exceptionNoticeFrequencyStrategy.getFrequencyType()) {
		case TIMEOUT:
			Duration dur = Duration.between(exceptionStatistics.getNoticeTime(), LocalDateTime.now());
			flag = exceptionNoticeFrequencyStrategy.getNoticeTimeInterval().compareTo(dur) < 0;
		case SHOWCOUNT:
			flag = exceptionStatistics.getShowCount().longValue() - exceptionStatistics.getLastNoticedCount()
					.longValue() > exceptionNoticeFrequencyStrategy.getNoticeShowCount().longValue();
		}
		return flag;
	}

}
