package com.kuding.microservice.events;

import org.springframework.context.ApplicationEvent;

public class ServiceCheckedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6065617682004318874L;

	private final String serviceId;

	public ServiceCheckedEvent(Object source, String serviceId) {
		super(source);
		this.serviceId = serviceId;
	}

	public String getServiceId() {
		return serviceId;
	}

}
