package com.kuding.microservice.control;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.TaskScheduler;

import com.kuding.microservice.interfaces.HealthCheckHandler;
import com.kuding.microservice.task.ServiceCheckTask;
import com.kuding.properties.servicemonitor.ServiceCheck;
import com.kuding.properties.servicemonitor.ServiceMonitorProperties;

public class ServiceCheckControl implements SmartInitializingSingleton, DisposableBean {

	private final TaskScheduler taskScheduler;

	private final ServiceMonitorProperties serviceMonitorProperties;

	Map<String, ScheduledFuture<?>> taskResultMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();

	private final Map<String, ServiceCheckTask> servicesMap = new ConcurrentHashMap<String, ServiceCheckTask>();

	private final DiscoveryClient discoveryClient;

	private final ApplicationEventPublisher applicationEventPublisher;

	private final HealthCheckHandler healthCheckHandler;

	private final Log logger = LogFactory.getLog(ServiceCheckControl.class);

	private ServiceCheck defaultServiceCheck;

	public ServiceCheckControl(TaskScheduler taskScheduler, ServiceMonitorProperties serviceMonitorProperties,
			DiscoveryClient discoveryClient, ApplicationEventPublisher applicationEventPublisher,
			HealthCheckHandler healthCheckHandler) {
		this.taskScheduler = taskScheduler;
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
		Map<String, ServiceCheck> map = serviceMonitorProperties.getMonitorServices();
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
			logger.debug("service check Existed:" + existed);
			return;
		}
		ServiceCheckTask serviceCheckTask = new ServiceCheckTask(service,
				serviceCheck == null ? defaultServiceCheck : serviceCheck, discoveryClient, healthCheckHandler,
				applicationEventPublisher);
		servicesMap.put(service, serviceCheckTask);
		ScheduledFuture<?> scheduledFuture = taskScheduler.scheduleAtFixedRate(serviceCheckTask,
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

	public ServiceMonitorProperties getServiceMonitorProperties() {
		return serviceMonitorProperties;
	}

	public Map<String, ServiceCheckTask> getServicesMap() {
		return servicesMap;
	}

	/**
	 * @return the defaultServiceCheck
	 */
	public ServiceCheck getDefaultServiceCheck() {
		return defaultServiceCheck;
	}

	/**
	 * @param defaultServiceCheck the defaultServiceCheck to set
	 */
	public void setDefaultServiceCheck(ServiceCheck defaultServiceCheck) {
		this.defaultServiceCheck = defaultServiceCheck;
	}

	/**
	 * @return the taskScheduler
	 */
	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	/**
	 * @return the discoveryClient
	 */
	public DiscoveryClient getDiscoveryClient() {
		return discoveryClient;
	}

	/**
	 * @return the applicationEventPublisher
	 */
	public ApplicationEventPublisher getApplicationEventPublisher() {
		return applicationEventPublisher;
	}

	/**
	 * @return the healthCheckHandler
	 */
	public HealthCheckHandler getHealthCheckHandler() {
		return healthCheckHandler;
	}

}
