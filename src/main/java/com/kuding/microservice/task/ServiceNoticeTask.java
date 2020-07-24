package com.kuding.microservice.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kuding.message.INoticeSendComponent;
import com.kuding.microservice.interfaces.ReportedFilterHandler;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.pojos.servicemonitor.MicroServiceReport;
import com.kuding.pojos.servicemonitor.ServiceCheckNotice;
import com.kuding.properties.PromethreusNoticeProperties;

public class ServiceNoticeTask implements Runnable {

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	private final List<INoticeSendComponent<ServiceCheckNotice>> noticeSendComponents;

	private final ReportedFilterHandler reportedFilterHandler;

	private final PromethreusNoticeProperties promethreusNoticeProperties;

	private final Log logger = LogFactory.getLog(ServiceNoticeTask.class);

	/**
	 * @param serviceCheckNoticeRepository
	 * @param noticeSendComponent
	 * @param reportedFilterHandler
	 * @param promethreusNoticeProperties
	 */
	public ServiceNoticeTask(ServiceCheckNoticeRepository serviceCheckNoticeRepository,
			List<INoticeSendComponent<ServiceCheckNotice>> noticeSendComponents,
			ReportedFilterHandler reportedFilterHandler, PromethreusNoticeProperties promethreusNoticeProperties) {
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
		this.noticeSendComponents = noticeSendComponents;
		this.reportedFilterHandler = reportedFilterHandler;
		this.promethreusNoticeProperties = promethreusNoticeProperties;
	}

	@Override
	public void run() {
		MicroServiceReport microServiceNotice = serviceCheckNoticeRepository.generateMicroServiceNotice();
		int problemCount = microServiceNotice.totalProblemCount();
		logger.debug("prepare for notice: \n " + microServiceNotice);
		microServiceNotice = reportedFilterHandler.filter(microServiceNotice);
		ServiceCheckNotice serviceCheckNotice = new ServiceCheckNotice(microServiceNotice,
				serviceCheckNoticeRepository.totalServiceCount(), promethreusNoticeProperties.getProjectEnviroment(),
				"服务监控通知");
		serviceCheckNotice.setProblemServiceCount(problemCount);
		if (serviceCheckNotice.needReport()) {
			noticeSendComponents.forEach(x -> x.send(serviceCheckNotice));
		}
	}

	public ServiceCheckNoticeRepository getServiceCheckNoticeRepository() {
		return serviceCheckNoticeRepository;
	}

	public ReportedFilterHandler getReportedFilterHandler() {
		return reportedFilterHandler;
	}

}
