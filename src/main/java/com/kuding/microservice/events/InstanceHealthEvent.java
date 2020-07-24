package com.kuding.microservice.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.kuding.pojos.servicemonitor.ServiceHealth;

public class InstanceHealthEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7508829413359680318L;

	private final String serviceName;

	private final List<ServiceHealth> list;

	public InstanceHealthEvent(Object source, String serviceName, List<ServiceHealth> list) {
		super(source);
		this.serviceName = serviceName;
		this.list = list;
	}

	public String getServiceName() {
		return serviceName;
	}

	public List<ServiceHealth> getList() {
		return list;
	}

}
