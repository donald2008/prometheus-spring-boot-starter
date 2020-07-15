package com.kuding.microservice.task;

import com.kuding.microservice.interfaces.IServiceMonitorNotice;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.microservice.pojo.MicroServiceNotice;

public class ServiceNoticeTask implements Runnable {

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	private final IServiceMonitorNotice serviceMonitorNotice;

	public ServiceNoticeTask(ServiceCheckNoticeRepository serviceCheckNoticeRepository,
			IServiceMonitorNotice serviceMonitorNotice) {
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
		this.serviceMonitorNotice = serviceMonitorNotice;
	}

	@Override
	public void run() {
		MicroServiceNotice microServiceNotice = serviceCheckNoticeRepository.generateMicroServiceNotice();
		serviceMonitorNotice.notice(microServiceNotice);
	}

}
