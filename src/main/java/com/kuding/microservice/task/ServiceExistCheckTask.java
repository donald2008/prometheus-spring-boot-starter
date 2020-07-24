package com.kuding.microservice.task;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;

import com.kuding.microservice.events.ServiceExistEvent;

public class ServiceExistCheckTask implements Runnable {

	private final Set<String> allService = new TreeSet<String>();

	private final DiscoveryClient discoveryClient;

	private final ApplicationEventPublisher applicationEventPublisher;

	private boolean authDetected = false;

	public ServiceExistCheckTask(Collection<String> allService, DiscoveryClient discoveryClient,
			ApplicationEventPublisher applicationEventPublisher, boolean authDetected) {
		this.allService.addAll(allService);
		this.discoveryClient = discoveryClient;
		this.applicationEventPublisher = applicationEventPublisher;
		this.authDetected = authDetected;
	}

	public boolean isAuthDetected() {
		return authDetected;
	}

	public Set<String> getAllService() {
		return allService;
	}

	public void setAuthDetected(boolean authDetected) {
		this.authDetected = authDetected;
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
		existedServices.forEach(x -> System.out.println(x));
		List<String> lackServices = allService.stream().filter(x -> !existedServices.contains(x)).collect(toList());
		List<String> additionalServices = Collections.emptyList();
		if (authDetected)
			additionalServices = existedServices.stream().filter(x -> !lackServices.contains(x)).collect(toList());
		applicationEventPublisher
				.publishEvent(new ServiceExistEvent(this, lackServices, additionalServices, existedServices.size()));
	}
}
