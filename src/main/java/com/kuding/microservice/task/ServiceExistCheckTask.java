package com.kuding.microservice.task;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;

import com.kuding.microservice.events.ServiceNotExistEvent;

public class ServiceExistCheckTask implements Runnable {

	private final List<String> allService;

	private final DiscoveryClient discoveryClient;

	private final ApplicationEventPublisher applicationEventPublisher;

	private boolean authDetected = false;

	public ServiceExistCheckTask(List<String> allService, DiscoveryClient discoveryClient,
			ApplicationEventPublisher applicationEventPublisher, boolean authDetected) {
		this.allService = allService;
		this.discoveryClient = discoveryClient;
		this.applicationEventPublisher = applicationEventPublisher;
		this.authDetected = authDetected;
	}

	public boolean isAuthDetected() {
		return authDetected;
	}

	public List<String> getAllService() {
		return allService;
	}

	public DiscoveryClient getDiscoveryClient() {
		return discoveryClient;
	}

	public ApplicationEventPublisher getApplicationEventPublisher() {
		return applicationEventPublisher;
	}

	@Override
	public void run() {
		List<String> existedServices = discoveryClient.getServices();
		List<String> lackServices = allService.stream().filter(x -> !existedServices.contains(x)).collect(toList());
		List<String> additionalServices = Collections.emptyList();
		if (authDetected)
			additionalServices = existedServices.stream().filter(x -> !lackServices.contains(x)).collect(toList());
		if (lackServices.size() > 0 || additionalServices.size() > 0)
			applicationEventPublisher.publishEvent(new ServiceNotExistEvent(this, lackServices, additionalServices));
	}
}
