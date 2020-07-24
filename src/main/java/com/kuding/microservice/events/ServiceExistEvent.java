package com.kuding.microservice.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

public class ServiceExistEvent extends ApplicationEvent {

	private static final long serialVersionUID = -1706134334761112080L;

	private final List<String> lackServices;

	private final List<String> additionalServices;

	private final int totalCount;

	/**
	 * @param source
	 * @param lackServices
	 * @param additionalServices
	 * @param totalCount
	 */
	public ServiceExistEvent(Object source, List<String> lackServices, List<String> additionalServices,
			int totalCount) {
		super(source);
		this.lackServices = lackServices;
		this.additionalServices = additionalServices;
		this.totalCount = totalCount;
	}

	public List<String> getLackServices() {
		return lackServices;
	}

	public List<String> getAdditionalServices() {
		return additionalServices;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

}
