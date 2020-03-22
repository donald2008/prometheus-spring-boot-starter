package com.kuding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.properties.ExceptionNoticeStorageProperties;
import com.kuding.storage.ExceptionNoticeStorage;
import com.kuding.storage.redis.ExceptionNoticeRedisStorage;

@Configuration
@ConditionalOnClass({ StringRedisTemplate.class })
@ConditionalOnProperty(name = "exceptionnotice.store.enable-redis-storage", havingValue = "true")
@ConditionalOnMissingBean(value = { ExceptionNoticeStorage.class })
@ConditionalOnBean({ ExceptionHandler.class })
@AutoConfigureAfter({ ExceptionNoticeConfig.class })
public class ExceptionNoticeRedisConfiguration {

	@Autowired
	private ExceptionNoticeStorageProperties exceptionNoticeStorageProperties;

	@Bean
	public ExceptionNoticeStorage exceptionRedisStorageComponent(StringRedisTemplate stringRedisTemplate) {
		ExceptionNoticeStorage exceptionRedisStorageComponent = new ExceptionNoticeRedisStorage(stringRedisTemplate,
				exceptionNoticeStorageProperties);
		return exceptionRedisStorageComponent;
	}

}
