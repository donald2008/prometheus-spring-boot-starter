package com.kuding.config.servicemonitor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.consul.discovery.ConditionalOnConsulDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecwid.consul.v1.ConsulClient;
import com.kuding.config.annos.ConditionalOnServiceMonitor;
import com.kuding.microservice.components.ConsulHealthCheckHandler;
import com.kuding.microservice.interfaces.HealthCheckHandler;

@Configuration
@ConditionalOnServiceMonitor
@ConditionalOnConsulDiscoveryEnabled
public class ConsulHealthCheckHandlerConfig {

	@Bean
	@ConditionalOnMissingBean
	public HealthCheckHandler healthCheckHandler(ConsulClient consulClient) {
		HealthCheckHandler handler = new ConsulHealthCheckHandler(consulClient);
		return handler;
	}
}
