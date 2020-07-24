package com.kuding.microservice.components;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.kuding.microservice.interfaces.ServiceCheckNoticeRepository;
import com.kuding.pojos.servicemonitor.MicroServiceReport;
import com.kuding.pojos.servicemonitor.ServiceHealthProblem;
import com.kuding.pojos.servicemonitor.ServiceInstanceLackProblem;

public class InMemeryServiceCheckNoticeRepository implements ServiceCheckNoticeRepository {

	private Map<String, ServiceInstanceLackProblem> instanceLackProblem = new TreeMap<>();

	private Map<String, ServiceHealthProblem> healthProblem = new TreeMap<>();

	private Set<String> lackServices = new TreeSet<String>();

	private Set<String> detectedService = new TreeSet<String>();

	private Map<String, List<String>> recoveredServices = new TreeMap<>();

	private Set<String> allService = new HashSet<String>();

	public InMemeryServiceCheckNoticeRepository(Collection<String> allService) {
		this.allService.addAll(allService);
	}

	@Override
	public void add(ServiceInstanceLackProblem serviceInstanceLackProblem) {
		String serviceName = serviceInstanceLackProblem.getServiceName();
		ServiceInstanceLackProblem problem = instanceLackProblem.get(serviceName);
		if (problem != null && serviceInstanceLackProblem.getLackCount() <= 0) {
			instanceLackProblem.remove(serviceName);
			recoveredServices.put(serviceName, Collections.emptyList());
		} else if (serviceInstanceLackProblem.getLackCount() > 0)
			instanceLackProblem.put(serviceName, serviceInstanceLackProblem);
	}

	@Override
	public void add(ServiceHealthProblem serviceHealthProblem) {
		String serviceName = serviceHealthProblem.getServiceName();
		ServiceHealthProblem problem = healthProblem.get(serviceName);
		if (problem != null) {
			List<String> recoverdInstance = problem.getUnhealthyInstances().stream()
					.filter(serviceHealthProblem.getHealthyInstances()::contains).collect(toList());
			if (recoverdInstance.size() > 0) {
				recoveredServices.put(serviceName, recoverdInstance);
			}
		}
		if (serviceHealthProblem.getUnhealthyInstances().size() == 0)
			healthProblem.remove(serviceName);
		else
			healthProblem.put(serviceName, serviceHealthProblem);
	}

	@Override
	public void add(List<String> lackServices, List<String> additionalServices) {
		this.detectedService.addAll(additionalServices);
		List<String> recoveredServices = this.lackServices.stream().filter(x -> !lackServices.contains(x))
				.collect(toList());
		if (recoveredServices.size() > 0)
			recoveredServices.forEach(x -> this.recoveredServices.put(x, Collections.emptyList()));
		this.lackServices.addAll(lackServices);
		this.allService.addAll(additionalServices);
	}

	@Override
	public MicroServiceReport generateMicroServiceNotice() {
		MicroServiceReport microServiceNotice = new MicroServiceReport();
		microServiceNotice.setHealthProblems(new TreeMap<String, ServiceHealthProblem>(healthProblem));
		microServiceNotice.setInstanceLackProblems(new TreeMap<>(instanceLackProblem));
		microServiceNotice.setLackServices(new HashSet<String>(lackServices));
		microServiceNotice.setRecoveredServicesInstances(new TreeMap<>(recoveredServices));
		detectedService.clear();
		recoveredServices.clear();
		return microServiceNotice;
	}

	@Override
	public int totalServiceCount() {
		return allService.size();
	}

	@Override
	public void clear() {
	}

}
