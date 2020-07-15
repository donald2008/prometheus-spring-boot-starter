package com.kuding.microservice.events;

import org.springframework.context.ApplicationListener;

import com.kuding.microservice.control.ServiceCheckControl;
import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;

public class ServiceNotExistedListener implements ApplicationListener<ServiceNotExistEvent> {

	private final ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	private final ServiceCheckControl serviceCheckControl;

	public ServiceNotExistedListener(ServiceCheckNoticeRepository serviceCheckNoticeRepository,
			ServiceCheckControl serviceCheckControl) {
		this.serviceCheckNoticeRepository = serviceCheckNoticeRepository;
		this.serviceCheckControl = serviceCheckControl;
	}

	@Override
	public void onApplicationEvent(ServiceNotExistEvent event) {
		serviceCheckNoticeRepository.add(event.getLackServices(), event.getAdditionalServices());
		if (event.getAdditionalServices().size() > 0) {
			serviceCheckControl.addAll(event.getAdditionalServices());
		}
	}

}
