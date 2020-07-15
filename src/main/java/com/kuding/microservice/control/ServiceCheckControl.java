package com.kuding.microservice.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import static java.util.stream.Collectors.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.kuding.microservice.interfaces.HealthCheckHandler;
import com.kuding.microservice.properties.ServiceCheck;
import com.kuding.microservice.properties.ServiceMonitorProperties;
import com.kuding.microservice.task.ServiceCheckTask;

public class ServiceCheckControl implements SmartInitializingSingleton, DisposableBean {

	private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private final ServiceMonitorProperties serviceMonitorProperties;

	Map<String, ScheduledFuture<?>> taskResultMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	private final Map<String, ServiceCheckTask> servicesMap = new ConcurrentHashMap<String, ServiceCheckTask>();

	private final DiscoveryClient discoveryClient;

	private final ApplicationEventPublisher applicationEventPublisher;

	private final HealthCheckHandler healthCheckHandler;

	private final Log logger = LogFactory.getLog(ServiceCheckControl.class);

	private ServiceCheck defaultServiceCheck;

	public ServiceCheckControl(ThreadPoolTaskScheduler threadPoolTaskScheduler,
			ServiceMonitorProperties serviceMonitorProperties, DiscoveryClient discoveryClient,
			ApplicationEventPublisher applicationEventPublisher, HealthCheckHandler healthCheckHandler) {
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
		this.serviceMonitorProperties = serviceMonitorProperties;
		this.discoveryClient = discoveryClient;
		this.applicationEventPublisher = applicationEventPublisher;
		this.healthCheckHandler = healthCheckHandler;
	}

	@Override
	public void destroy() throws Exception {
		taskResultMap.forEach((x, y) -> {
			y.cancel(false);
		});
	}

	@Override
	public void afterSingletonsInstantiated() {
		Map<String, ServiceCheck> map = new HashMap<String, ServiceCheck>(
				serviceMonitorProperties.getMonitorServices());
		defaultServiceCheck = map.remove("default");
		if (serviceMonitorProperties.isAutoDiscovery()) {
			List<String> services = discoveryClient.getServices();
			Set<String> set = map.keySet();
			List<String> additionalServices = services.stream().filter(x -> !set.contains(x)).collect(toList());
			if (additionalServices.size() > 0) {
				logger.info("service auto detected , so can not add :" + additionalServices);
				additionalServices.forEach(x -> map.put(x, defaultServiceCheck));
			}
		}
		map.forEach((x, y) -> add(x, y));
	}

	public void add(String service, ServiceCheck serviceCheck) {
		ServiceCheckTask existed = servicesMap.get(service);
		if (existed != null) {
			logger.warn("service check Existed:" + existed);
			return;
		}
		ServiceCheckTask serviceCheckTask = new ServiceCheckTask(service,
				serviceCheck == null ? serviceMonitorProperties.getMonitorServices().get("default") : serviceCheck,
				discoveryClient, healthCheckHandler, applicationEventPublisher);
		servicesMap.put(service, serviceCheckTask);
		ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.scheduleAtFixedRate(serviceCheckTask,
				serviceCheck.getCheckInterval());
		taskResultMap.put(service, scheduledFuture);
	}

	public void addAll(List<String> additionalServices) {
		additionalServices.forEach(x -> add(x, defaultServiceCheck));
	}

	public Map<String, ScheduledFuture<?>> getTaskResultMap() {
		return taskResultMap;
	}

	public void setTaskResultMap(Map<String, ScheduledFuture<?>> taskResultMap) {
		this.taskResultMap = taskResultMap;
	}

	public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
		return threadPoolTaskScheduler;
	}

	public ServiceMonitorProperties getServiceMonitorProperties() {
		return serviceMonitorProperties;
	}

	public Map<String, ServiceCheckTask> getServicesMap() {
		return servicesMap;
	}

}
