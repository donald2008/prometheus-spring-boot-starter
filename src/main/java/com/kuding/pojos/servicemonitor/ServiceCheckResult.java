//package com.kuding.pojos.servicemonitor;
//
//import java.time.LocalDateTime;
//import java.util.Collection;
//import java.util.Set;
//import java.util.TreeSet;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import com.kuding.properties.servicemonitor.ServiceCheck;
//
//public class ServiceCheckResult {
//
//	private final String serviceName;
//
//	private Set<String> instances;
//
//	private Set<String> unhealthyInstances = new TreeSet<>();
//
//	private Set<String> healthyInstances = new TreeSet<>();
//
//	private final ServiceCheck serviceCheck;
//
//	private AtomicInteger checkCount = new AtomicInteger(0);
//
//	private LocalDateTime createTime = LocalDateTime.now();
//
//	/**
//	 * @param serviceName
//	 * @param instances
//	 * @param serviceCheck
//	 */
//	public ServiceCheckResult(String serviceName, Set<String> instances, ServiceCheck serviceCheck) {
//		this.serviceName = serviceName;
//		this.instances = instances;
//		this.serviceCheck = serviceCheck;
//	}
//
//	/**
//	 * @return the serviceName
//	 */
//	public String getServiceName() {
//		return serviceName;
//	}
//
//	/**
//	 * @return the instances
//	 */
//	public Set<String> getInstances() {
//		return instances;
//	}
//
//	/**
//	 * @param instances the instances to set
//	 */
//	public void setInstances(Set<String> instances) {
//		this.instances = instances;
//	}
//
//	/**
//	 * @return the unhealthyInstances
//	 */
//	public Set<String> getUnhealthyInstances() {
//		return unhealthyInstances;
//	}
//
//	/**
//	 * @param unhealthyInstances the unhealthyInstances to set
//	 */
//	public void setUnhealthyInstances(Set<String> unhealthyInstances) {
//		this.unhealthyInstances = unhealthyInstances;
//	}
//
//	/**
//	 * @return the serviceCheck
//	 */
//	public ServiceCheck getServiceCheck() {
//		return serviceCheck;
//	}
//
//	/**
//	 * @return the checkCount
//	 */
//	public AtomicInteger getCheckCount() {
//		return checkCount;
//	}
//
//	/**
//	 * @param checkCount the checkCount to set
//	 */
//	public void setCheckCount(AtomicInteger checkCount) {
//		this.checkCount = checkCount;
//	}
//
//	/**
//	 * @return the createTime
//	 */
//	public LocalDateTime getCreateTime() {
//		return createTime;
//	}
//
//	/**
//	 * @param createTime the createTime to set
//	 */
//	public void setCreateTime(LocalDateTime createTime) {
//		this.createTime = createTime;
//	}
//
//	public boolean haveUnhealthInstance() {
//		return unhealthyInstances.size() > 0;
//	}
//
//	public boolean lackOfInstance() {
//		return instances.size() < serviceCheck.getServiceCount();
//	}
//
//	public void addAllUnhealthyInstances(Collection<String> unhealthyInstances, Collection<String> healthyInstances) {
//		this.unhealthyInstances.addAll(unhealthyInstances);
//		this.healthyInstances.addAll(healthyInstances);
//	}
//
//	public ServiceHealthProblem getUnHealthProblem() {
//		return new ServiceHealthProblem(serviceName, new TreeSet<String>(healthyInstances),
//				new TreeSet<String>(unhealthyInstances));
//	}
//
//	public ServiceInstanceLackProblem getInstanceLackProblem() {
//		return new ServiceInstanceLackProblem(serviceName, new TreeSet<String>(healthyInstances),
//				serviceCheck.getServiceCount() - healthyInstances.size());
//	}
//
//	public void check(ServiceCheckResult serviceCheckResult) {
//
//	}
//}
