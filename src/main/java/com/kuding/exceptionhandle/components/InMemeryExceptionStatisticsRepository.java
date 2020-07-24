package com.kuding.exceptionhandle.components;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.kuding.exceptionhandle.interfaces.ExceptionNoticeStatisticsRepository;
import com.kuding.pojos.ExceptionNotice;
import com.kuding.pojos.ExceptionStatistics;

public class InMemeryExceptionStatisticsRepository implements ExceptionNoticeStatisticsRepository {

	private final Map<String, ExceptionStatistics> map = Collections.synchronizedMap(new HashMap<>());

	@Override
	public ExceptionStatistics increaseOne(ExceptionNotice exceptionNotice) {
		ExceptionStatistics exceptionStatistics = map.getOrDefault(exceptionNotice.getUid(),
				new ExceptionStatistics(exceptionNotice.getUid()));
		if (exceptionStatistics.isFirstCreated()) {
			synchronized (exceptionStatistics) {
				map.merge(exceptionStatistics.getUid(), exceptionStatistics, (x, y) -> {
					if (x == null) {
						return y;
					} else {
						x.setFirstCreated(false);
						x.plusOne();
						return x;
					}
				});
			}
		}
		exceptionStatistics.plusOne();
		return exceptionStatistics;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public void increaseShowOne(ExceptionStatistics exceptionStatistics) {
		exceptionStatistics.refreshShow();
	}

}
