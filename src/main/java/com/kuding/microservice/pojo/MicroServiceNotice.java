package com.kuding.microservice.pojo;

import java.util.List;

public class MicroServiceNotice {

	/**
	 * 缺失的服务
	 */
	private List<String> lackServices;

	/**
	 * 恢复的服务
	 */
	private List<String> recoveredService;

	/**
	 * 缺少实例的服务
	 */
	private List<ServiceInstanceLackProblem> instanceLackProblems;

	/**
	 * 恢复实例的服务
	 */
	private List<ServiceInstanceLackProblem> recoveredInstanceLackProblems;

	/**
	 * 不健康的服务
	 */
	private List<ServiceHealthProblem> healthProblems;

	/**
	 * 恢复健康的服务
	 */
	private List<ServiceHealthProblem> recoveredHealthProblems;

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

	public List<ServiceInstanceLackProblem> getInstanceLackProblems() {
		return instanceLackProblems;
	}

	public void setInstanceLackProblems(List<ServiceInstanceLackProblem> instanceLackProblems) {
		this.instanceLackProblems = instanceLackProblems;
	}

	public List<ServiceInstanceLackProblem> getRecoveredInstanceLackProblems() {
		return recoveredInstanceLackProblems;
	}

	public void setRecoveredInstanceLackProblems(List<ServiceInstanceLackProblem> recoveredInstanceLackProblems) {
		this.recoveredInstanceLackProblems = recoveredInstanceLackProblems;
	}

	public List<ServiceHealthProblem> getHealthProblems() {
		return healthProblems;
	}

	public void setHealthProblems(List<ServiceHealthProblem> healthProblems) {
		this.healthProblems = healthProblems;
	}

	public List<ServiceHealthProblem> getRecoveredHealthProblems() {
		return recoveredHealthProblems;
	}

	public void setRecoveredHealthProblems(List<ServiceHealthProblem> recoveredHealthProblems) {
		this.recoveredHealthProblems = recoveredHealthProblems;
	}
	
	
}
