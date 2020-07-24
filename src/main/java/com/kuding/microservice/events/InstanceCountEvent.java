package com.kuding.microservice.events;

import java.util.Set;

import org.springframework.context.ApplicationEvent;

public class InstanceCountEvent extends ApplicationEvent {

	private static final long serialVersionUID = 3930120792129192184L;

	private final String serviceName;

	private final int lackCount;

	private final Set<String> instanceIds;

	public InstanceCountEvent(Object source, String serviceName, int lackCount, Set<String> instanceIds) {
		super(source);
		this.serviceName = serviceName;
		this.lackCount = lackCount;
		this.instanceIds = instanceIds;
	}

	public String getServiceName() {
		return serviceName;
	}

	public int getLackCount() {
		return lackCount;
	}

	public Set<String> getInstanceIds() {
		return instanceIds;
	}

}
