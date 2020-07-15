package com.kuding.microservice.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.microservice.pojo.ServiceHealthProblem;
import com.kuding.microservice.pojo.ServiceInstanceLackProblem;

public class ServiceProblemListener implements ApplicationListener<ServiceProblemEvent> {

	@Autowired
	private ServiceCheckNoticeRepository serviceCheckNoticeRepository;

	@Override
	public void onApplicationEvent(ServiceProblemEvent event) {
		switch (event.getServiceProblem()) {
		case INSTANCE_LACK:
			ServiceInstanceLackProblem serviceInstanceLackProblem = new ServiceInstanceLackProblem(event);
			serviceCheckNoticeRepository.add(serviceInstanceLackProblem);
			break;
		case INSTANCE_NOT_HEALTHY:
			ServiceHealthProblem serviceHealthProblem = new ServiceHealthProblem(event);
			serviceCheckNoticeRepository.add(serviceHealthProblem);
			break;
		case NOT_FOUND:
			break;
		}
	}
}
