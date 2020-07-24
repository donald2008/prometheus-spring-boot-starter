package com.kuding.microservice.interfaces;

import java.util.List;

import com.kuding.pojos.servicemonitor.MicroServiceReport;
import com.kuding.pojos.servicemonitor.ServiceHealthProblem;
import com.kuding.pojos.servicemonitor.ServiceInstanceLackProblem;

public interface ServiceCheckNoticeRepository {

	void add(ServiceInstanceLackProblem serviceInstanceLackProblem);

	void add(ServiceHealthProblem serviceHealthProblem);

	void add(List<String> lackServices, List<String> additionalServices);

	MicroServiceReport generateMicroServiceNotice();

	int totalServiceCount();

	void clear();
}
