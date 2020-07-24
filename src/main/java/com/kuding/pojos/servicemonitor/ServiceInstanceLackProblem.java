package com.kuding.pojos.servicemonitor;

import java.util.Set;

import com.kuding.microservice.events.InstanceCountEvent;

public class ServiceInstanceLackProblem extends ServiceProblem {

	private Set<String> instanceIds;

	private int lackCount;

	public ServiceInstanceLackProblem(InstanceCountEvent instanceCountEvent) {
		serviceName = instanceCountEvent.getServiceName();
		instanceIds = instanceCountEvent.getInstanceIds();
		this.lackCount = instanceCountEvent.getLackCount();
	}

	/**
	 * @param instanceIds
	 * @param lackCount
	 */
	public ServiceInstanceLackProblem(String serviceName, Set<String> instanceIds, int lackCount) {
		this.serviceName = serviceName;
		this.instanceIds = instanceIds;
		this.lackCount = lackCount;
	}

	public Set<String> getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(Set<String> instanceIds) {
		this.instanceIds = instanceIds;
	}

	public int getLackCount() {
		return lackCount;
	}

	public void setLackCount(int lackCount) {
		this.lackCount = lackCount;
	}

	@Override
	public String toString() {
		return "ServiceInstanceLackProblem [instanceIds=" + instanceIds + ", lackCount=" + lackCount + ", serviceName="
				+ serviceName + "]";
	}

}
