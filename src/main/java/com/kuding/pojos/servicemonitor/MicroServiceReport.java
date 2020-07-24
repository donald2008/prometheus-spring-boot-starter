package com.kuding.pojos.servicemonitor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MicroServiceReport {

	/**
	 * 缺失的服务
	 */
	private Set<String> lackServices = Collections.emptySet();

	/**
	 * 缺少实例的服务
	 */
	private Map<String, ServiceInstanceLackProblem> instanceLackProblems = Collections.emptyMap();

	/**
	 * 不健康的服务
	 */
	private Map<String, ServiceHealthProblem> healthProblems = Collections.emptyMap();

	/**
	 * 恢复的服务与实例
	 */
	private Map<String, List<String>> recoveredServicesInstances = Collections.emptyMap();

	public Set<String> getLackServices() {
		return lackServices;
	}

	public void setLackServices(Set<String> lackServices) {
		this.lackServices = lackServices;
	}

	public Map<String, ServiceInstanceLackProblem> getInstanceLackProblems() {
		return instanceLackProblems;
	}

	public void setInstanceLackProblems(Map<String, ServiceInstanceLackProblem> instanceLackProblems) {
		this.instanceLackProblems = instanceLackProblems;
	}

	public Map<String, ServiceHealthProblem> getHealthProblems() {
		return healthProblems;
	}

	public void setHealthProblems(Map<String, ServiceHealthProblem> healthProblems) {
		this.healthProblems = healthProblems;
	}

	public Map<String, List<String>> getRecoveredServicesInstances() {
		return recoveredServicesInstances;
	}

	public void setRecoveredServicesInstances(Map<String, List<String>> recoveredServicesInstances) {
		this.recoveredServicesInstances = recoveredServicesInstances;
	}

	@Override
	public String toString() {
		return "MicroServiceReport [lackServices=" + lackServices + ", instanceLackProblems=" + instanceLackProblems
				+ ", healthProblems=" + healthProblems + ", recoveredServicesInstances=" + recoveredServicesInstances
				+ "]";
	}

	public int totalProblemCount() {
		return healthProblems.size() + instanceLackProblems.size() + lackServices.size();
	}

}
