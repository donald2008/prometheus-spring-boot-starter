package com.kuding.microservice.control;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;

import com.kuding.message.INoticeSendComponent;
import com.kuding.microservice.interfaces.ReportedFilterHandler;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.microservice.task.ServiceNoticeTask;
import com.kuding.pojos.servicemonitor.ServiceCheckNotice;
import com.kuding.properties.PromethreusNoticeProperties;
import com.kuding.properties.servicemonitor.ServiceMonitorProperties;

public class ServiceNoticeControl implements SmartInitializingSingleton, DisposableBean {

	private final Log logger = LogFactory.getLog(ServiceNoticeControl.class);

	private final ServiceMonitorProperties serviceMonitorProperties;

	private final PromethreusNoticeProperties promethreusNoticeProperties;

	private final TaskScheduler taskScheduler;

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	private final List<INoticeSendComponent<ServiceCheckNotice>> noticeSendComponents;

	private final ReportedFilterHandler reportedFilterHandler;

	private ScheduledFuture<?> result;

	/**
	 * @param serviceMonitorProperties
	 * @param promethreusNoticeProperties
	 * @param taskScheduler
	 * @param serviceCheckNoticeRepository
	 * @param noticeSendComponent
	 * @param reportedFilterHandler
	 * @param result
	 */
	public ServiceNoticeControl(ServiceMonitorProperties serviceMonitorProperties,
			PromethreusNoticeProperties promethreusNoticeProperties, TaskScheduler taskScheduler,
			ServiceCheckNoticeRepository serviceCheckNoticeRepository,
			List<INoticeSendComponent<ServiceCheckNotice>> noticeSendComponents,
			ReportedFilterHandler reportedFilterHandler) {
		this.serviceMonitorProperties = serviceMonitorProperties;
		this.promethreusNoticeProperties = promethreusNoticeProperties;
		this.taskScheduler = taskScheduler;
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
		this.noticeSendComponents = noticeSendComponents;
		this.reportedFilterHandler = reportedFilterHandler;
	}

	public ServiceMonitorProperties getServiceMonitorProperties() {
		return serviceMonitorProperties;
	}

	/**
	 * @return the result
	 */
	public ScheduledFuture<?> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(ScheduledFuture<?> result) {
		this.result = result;
	}

	/**
	 * @return the taskScheduler
	 */
	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	/**
	 * @return the serviceCheckNoticeRepository
	 */
	public ServiceCheckNoticeRepository getServiceCheckNoticeRepository() {
		return serviceCheckNoticeRepository;
	}

	/**
	 * @return the reportedFilterHandler
	 */
	public ReportedFilterHandler getReportedFilterHandler() {
		return reportedFilterHandler;
	}

	@Override
	public void destroy() throws Exception {
		result.cancel(false);
	}

	@Override
	public void afterSingletonsInstantiated() {
		logger.debug("开启通知任务");
		ServiceNoticeTask serviceNoticeTask = new ServiceNoticeTask(serviceCheckNoticeRepository, noticeSendComponents,
				reportedFilterHandler, promethreusNoticeProperties);
		PeriodicTrigger trigger = new PeriodicTrigger(
				serviceMonitorProperties.getServiceCheckNoticeInterval().toMillis());
		trigger.setInitialDelay(30000);
		result = taskScheduler.schedule(serviceNoticeTask, trigger);
	}
}
