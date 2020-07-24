package com.kuding.microservice.task;

import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;

import com.kuding.microservice.events.InstanceCountEvent;
import com.kuding.microservice.events.InstanceHealthEvent;
import com.kuding.microservice.interfaces.HealthCheckHandler;
import com.kuding.pojos.servicemonitor.ServiceHealth;
import com.kuding.pojos.servicemonitor.ServiceStatus;
import com.kuding.properties.servicemonitor.ServiceCheck;

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
		List<ServiceHealth> list = new ArrayList<ServiceHealth>(servicesMap.size());
		servicesMap.forEach((x, y) -> {
			list.add(new ServiceHealth(y.getInstanceId(),
					healthCheckHandler.isHealthy(y, serviceCheck) ? ServiceStatus.UP : ServiceStatus.DOWN));
		});
		applicationEventPublisher.publishEvent(new InstanceHealthEvent(this, serviceId, list));
		checked = true;
	}

	public void freshInstance() {
		List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
		list.forEach(x -> System.out.println(x));
		applicationEventPublisher
				.publishEvent(new InstanceCountEvent(this, serviceId, serviceCheck.getServiceCount() - list.size(),
						list.stream().map(ServiceInstance::getInstanceId).collect(toSet())));
		servicesMap.clear();
		list.forEach(x -> servicesMap.put(x.getInstanceId(), x));
	}

	@Override
	public String toString() {
		return new StringBuilder().append("ServiceCheckTask:").append(serviceId).append("-->")
				.append(serviceCheck.toString()).append("\n").toString();
	}

}
