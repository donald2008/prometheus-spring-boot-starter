package com.kuding.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exceptionnotice.store")
public class ExceptionNoticeStorageProperties {

	/**
	 * 开启redis存储
	 */
	private boolean enableRedisStorage = false;

	/**
	 * redis的键
	 */
	private String redisKey = "prometheus-notice";

	public boolean isEnableRedisStorage() {
		return enableRedisStorage;
	}

	public void setEnableRedisStorage(boolean enableRedisStorage) {
		this.enableRedisStorage = enableRedisStorage;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public void setRedisKey(String redisKey) {
		this.redisKey = redisKey;
	}

}
