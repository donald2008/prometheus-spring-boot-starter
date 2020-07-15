package com.kuding.microservice.interfaces;

import java.util.List;

import com.kuding.microservice.pojo.MicroServiceNotice;
import com.kuding.microservice.pojo.ServiceHealthProblem;
import com.kuding.microservice.pojo.ServiceInstanceLackProblem;

public interface ServiceCheckNoticeRepository {

	void add(ServiceInstanceLackProblem serviceInstanceLackProblem);

	void add(ServiceHealthProblem serviceHealthProblem);

	void add(List<String> lackServices, List<String> additionalServices);

	MicroServiceNotice generateMicroServiceNotice();

}
