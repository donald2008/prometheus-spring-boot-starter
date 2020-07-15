package com.kuding.microservice.components;

import java.util.List;

import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.microservice.pojo.MicroServiceNotice;
import com.kuding.microservice.pojo.ServiceHealthProblem;
import com.kuding.microservice.pojo.ServiceInstanceLackProblem;

public class InMemeryServiceCheckNoticeRepository implements ServiceCheckNoticeRepository {

	@Override
	public void add(ServiceInstanceLackProblem serviceInstanceLackProblem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(ServiceHealthProblem serviceHealthProblem) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(List<String> lackServices, List<String> additionalServices) {
		// TODO Auto-generated method stub

	}

	@Override
	public MicroServiceNotice generateMicroServiceNotice() {
		// TODO Auto-generated method stub
		return null;
	}

}
