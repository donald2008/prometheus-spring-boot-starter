package com.kuding.microservice.events;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.ApplicationEvent;

import com.kuding.microservice.pojo.ServiceProblemType;

public class ServiceProblemEvent extends ApplicationEvent {

	private String serviceId;

	private List<ServiceInstance> instances;

	private int lackCount = 0;

	private ServiceProblemType serviceProblem;

	private static final long serialVersionUID = -9035604762955843962L;

	public ServiceProblemEvent(Object source, String serviceId, List<ServiceInstance> problemInstances, int lackCount,
			ServiceProblemType serviceProblem) {
		super(source);
		this.serviceId = serviceId;
		this.instances = problemInstances;
		this.lackCount = lackCount;
		this.serviceProblem = serviceProblem;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<ServiceInstance> getInstances() {
		return instances;
	}

	public void setInstances(List<ServiceInstance> instances) {
		this.instances = instances;
	}

	public int getLackCount() {
		return lackCount;
	}

	public void setLackCount(int lackCount) {
		this.lackCount = lackCount;
	}

	public ServiceProblemType getServiceProblem() {
		return serviceProblem;
	}

	public void setServiceProblem(ServiceProblemType serviceProblem) {
		this.serviceProblem = serviceProblem;
	}

}
