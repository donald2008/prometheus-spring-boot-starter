package com.kuding.storage.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.kuding.content.ExceptionNotice;
import com.kuding.properties.ExceptionNoticeStorageProperties;
import com.kuding.storage.ExceptionNoticeStorage;

public class ExceptionNoticeRedisStorage implements ExceptionNoticeStorage {

	private final StringRedisTemplate stringRedisTemplate;

	private final ExceptionNoticeStorageProperties exceptionNoticeStorageProperties;

	public ExceptionNoticeRedisStorage(StringRedisTemplate stringRedisTemplate,
			ExceptionNoticeStorageProperties exceptionNoticeStorageProperties) {
		super();
		this.stringRedisTemplate = stringRedisTemplate;
		this.exceptionNoticeStorageProperties = exceptionNoticeStorageProperties;
	}

	@Override
	public void saveExcepion(ExceptionNotice exceptionNotice) {
		stringRedisTemplate.opsForHash().put(exceptionNoticeStorageProperties.getRedisKey(), exceptionNotice.getUid(),
				exceptionNotice.createText());
	}

}
