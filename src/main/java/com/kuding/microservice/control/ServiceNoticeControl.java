package com.kuding.microservice.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.microservice.properties.ServiceMonitorProperties;
import com.kuding.microservice.task.ServiceNoticeTask;

public class ServiceNoticeControl implements SmartInitializingSingleton, DisposableBean {

	private final Log logger = LogFactory.getLog(ServiceNoticeControl.class);

	private final ServiceMonitorProperties serviceMonitorProperties;

	private final ServiceNoticeTask serviceNoticeTask;

	private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	public ServiceNoticeControl(ServiceMonitorProperties serviceMonitorProperties, ServiceNoticeTask serviceNoticeTask,
			ThreadPoolTaskScheduler threadPoolTaskScheduler,
			ServiceCheckNoticeRepository serviceCheckNoticeRepository) {
		this.serviceMonitorProperties = serviceMonitorProperties;
		this.serviceNoticeTask = serviceNoticeTask;
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
	}

	public ServiceMonitorProperties getServiceMonitorProperties() {
		return serviceMonitorProperties;
	}

	public ServiceNoticeTask getServiceNoticeTask() {
		return serviceNoticeTask;
	}

	public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
		return threadPoolTaskScheduler;
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterSingletonsInstantiated() {
		// TODO Auto-generated method stub

	}
}
