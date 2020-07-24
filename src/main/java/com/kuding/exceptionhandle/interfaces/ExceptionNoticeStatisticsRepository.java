package com.kuding.exceptionhandle.interfaces;

import com.kuding.pojos.ExceptionNotice;
import com.kuding.pojos.ExceptionStatistics;

public interface ExceptionNoticeStatisticsRepository {

	public ExceptionStatistics increaseOne(ExceptionNotice exceptionNotice);

	public void increaseShowOne(ExceptionStatistics exceptionStatistics);

	public void clear();
}
