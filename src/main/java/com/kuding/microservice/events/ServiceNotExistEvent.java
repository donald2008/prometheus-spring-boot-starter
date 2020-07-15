package com.kuding.microservice.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

public class ServiceNotExistEvent extends ApplicationEvent {

	private static final long serialVersionUID = -1706134334761112080L;

	private final List<String> lackServices;

	private final List<String> additionalServices;

	public ServiceNotExistEvent(Object source, List<String> lackServices, List<String> additionalServices) {
		super(source);
		this.lackServices = lackServices;
		this.additionalServices = additionalServices;
	}

	public List<String> getLackServices() {
		return lackServices;
	}

	public List<String> getAdditionalServices() {
		return additionalServices;
	}

}
