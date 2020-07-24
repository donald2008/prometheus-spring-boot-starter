package com.kuding.microservice.interfaces;

import org.springframework.cloud.client.ServiceInstance;

import com.kuding.properties.servicemonitor.ServiceCheck;

@FunctionalInterface
public interface HealthCheckHandler {

	public boolean isHealthy(ServiceInstance serviceInstance, ServiceCheck serviceCheck);

}
