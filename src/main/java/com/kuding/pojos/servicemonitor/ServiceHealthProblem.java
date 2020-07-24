package com.kuding.pojos.servicemonitor;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.kuding.microservice.events.InstanceHealthEvent;

public class ServiceHealthProblem extends ServiceProblem {

	private Set<String> healthyInstances = Collections.emptySet();

	private Set<String> unhealthyInstances = Collections.emptySet();

	public ServiceHealthProblem(InstanceHealthEvent serviceProblemEvent) {
		this.serviceName = serviceProblemEvent.getServiceName();
		Map<ServiceStatus, Set<String>> map = serviceProblemEvent.getList().stream()
				.collect(groupingBy(ServiceHealth::getStatus, mapping(ServiceHealth::getInstanceId, toSet())));
		healthyInstances = map.getOrDefault(ServiceStatus.UP, Collections.emptySet());
		unhealthyInstances = map.getOrDefault(ServiceStatus.DOWN, Collections.emptySet());
	}

	public ServiceHealthProblem(String serviceName, Set<String> healthyInstances, Set<String> unhealthyInstances) {
		this.serviceName = serviceName;
		this.healthyInstances = healthyInstances;
		this.unhealthyInstances = unhealthyInstances;
	}

	public Set<String> getHealthyInstances() {
		return healthyInstances;
	}

	public void setHealthyInstances(Set<String> healthyInstances) {
		this.healthyInstances = healthyInstances;
	}

	public Set<String> getUnhealthyInstances() {
		return unhealthyInstances;
	}

	public void setUnhealthyInstances(Set<String> unhealthyInstances) {
		this.unhealthyInstances = unhealthyInstances;
	}

	@Override
	public String toString() {
		return "ServiceHealthProblem [healthyInstances=" + healthyInstances + ", unhealthyInstances="
				+ unhealthyInstances + ", serviceName=" + serviceName + "]";
	}

}
