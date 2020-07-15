package com.kuding.microservice.pojo;

import java.time.LocalDateTime;
import java.util.List;

public class ServiceCheckNotice {

	private List<MicroServiceNotice> problemServices;

	private List<String> lackServices;

	private List<String> recoveredService;

	private int totalServiceCount;

	private int problemServiceCount;

	private LocalDateTime noticeTime;

	public List<MicroServiceNotice> getProblemServices() {
		return problemServices;
	}

	public void setProblemServices(List<MicroServiceNotice> problemServices) {
		this.problemServices = problemServices;
	}

	public List<String> getLackServices() {
		return lackServices;
	}

	public void setLackServices(List<String> lackServices) {
		this.lackServices = lackServices;
	}

	public List<String> getRecoveredService() {
		return recoveredService;
	}

	public void setRecoveredService(List<String> recoveredService) {
		this.recoveredService = recoveredService;
	}

	public int getTotalServiceCount() {
		return totalServiceCount;
	}

	public void setTotalServiceCount(int totalServiceCount) {
		this.totalServiceCount = totalServiceCount;
	}

	public int getProblemServiceCount() {
		return problemServiceCount;
	}

	public void setProblemServiceCount(int problemServiceCount) {
		this.problemServiceCount = problemServiceCount;
	}

	public LocalDateTime getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(LocalDateTime noticeTime) {
		this.noticeTime = noticeTime;
	}

}
