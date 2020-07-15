package com.kuding.microservice.pojo;

import java.util.List;
import static java.util.stream.Collectors.*;

import org.springframework.cloud.client.ServiceInstance;

import com.kuding.microservice.events.ServiceProblemEvent;

public class ServiceInstanceLackProblem extends ServiceProblem {

	private List<String> instanceIds;

	private int lackCount;

	public ServiceInstanceLackProblem(ServiceProblemEvent serviceProblemEvent) {
		super();
		serviceName = serviceProblemEvent.getServiceId();
		instanceIds = serviceProblemEvent.getInstances().stream().map(ServiceInstance::getInstanceId).collect(toList());
		this.lackCount = serviceProblemEvent.getLackCount();
	}

	public List<String> getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(List<String> instanceIds) {
		this.instanceIds = instanceIds;
	}

	public int getLackCount() {
		return lackCount;
	}

	public void setLackCount(int lackCount) {
		this.lackCount = lackCount;
	}

}
