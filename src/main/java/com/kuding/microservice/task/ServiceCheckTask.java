package com.kuding.microservice.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;

import com.kuding.microservice.events.ServiceProblemEvent;
import com.kuding.microservice.interfaces.HealthCheckHandler;
import com.kuding.microservice.pojo.ServiceProblemType;
import com.kuding.microservice.properties.ServiceCheck;

public class ServiceCheckTask implements Runnable {

	private final String serviceId;

	private final ServiceCheck serviceCheck;

	private final DiscoveryClient discoveryClient;

	private final HealthCheckHandler healthCheckHandler;

	private final ApplicationEventPublisher applicationEventPublisher;

	private volatile boolean checked = false;

	private Map<String, ServiceInstance> servicesMap = new HashMap<String, ServiceInstance>();

	public ServiceCheckTask(String serviceId, ServiceCheck serviceCheck, DiscoveryClient discoveryClient,
			HealthCheckHandler healthCheckHandler, ApplicationEventPublisher applicationEventPublisher) {
		this.serviceId = serviceId;
		this.serviceCheck = serviceCheck;
		this.discoveryClient = discoveryClient;
		this.healthCheckHandler = healthCheckHandler;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public ServiceCheck getServiceCheck() {
		return serviceCheck;
	}

	public Map<String, ServiceInstance> getServicesMap() {
		return servicesMap;
	}

	public void setServicesMap(Map<String, ServiceInstance> servicesMap) {
		this.servicesMap = servicesMap;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public void run() {
		freshInstance();
		List<ServiceInstance> list = new ArrayList<ServiceInstance>(servicesMap.size());
		servicesMap.forEach((x, y) -> {
			if (!healthCheckHandler.isHealthy(y, serviceCheck))
				list.add(y);
		});
		if (list.size() > 0) {
			applicationEventPublisher.publishEvent(
					new ServiceProblemEvent(this, serviceId, list, 0, ServiceProblemType.INSTANCE_NOT_HEALTHY));
		}
		checked = true;
	}

	public void freshInstance() {
		List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
		if (list.size() < serviceCheck.getServiceCount())
			applicationEventPublisher.publishEvent(new ServiceProblemEvent(this, serviceId, list,
					serviceCheck.getServiceCount() - list.size(), ServiceProblemType.INSTANCE_LACK));
		servicesMap.clear();
		list.forEach(x -> servicesMap.put(x.getInstanceId(), x));
	}

	@Override
	public String toString() {
		return new StringBuilder().append("ServiceCheckTask:").append(serviceId).append("-->")
				.append(serviceCheck.toString()).append("\n").toString();
	}

}
