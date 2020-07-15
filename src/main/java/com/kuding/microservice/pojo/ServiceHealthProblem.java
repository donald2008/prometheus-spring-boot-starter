package com.kuding.microservice.pojo;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.*;
import org.springframework.cloud.client.ServiceInstance;

import com.kuding.microservice.events.ServiceProblemEvent;

public class ServiceHealthProblem extends ServiceProblem {

	private List<String> instanceIds;

	private LocalDateTime noticeTime;

	public ServiceHealthProblem(ServiceProblemEvent serviceProblemEvent) {
		this.serviceName = serviceProblemEvent.getServiceId();
		this.instanceIds = serviceProblemEvent.getInstances().stream().map(ServiceInstance::getInstanceId)
				.collect(toList());
		this.noticeTime = LocalDateTime.now();
	}

	public List<String> getInstanceIds() {
		return instanceIds;
	}

	public void setInstanceIds(List<String> instanceIds) {
		this.instanceIds = instanceIds;
	}

	public LocalDateTime getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(LocalDateTime noticeTime) {
		this.noticeTime = noticeTime;
	}

}
