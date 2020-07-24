package com.kuding.pojos.servicemonitor;

import java.time.format.DateTimeFormatter;

import com.kuding.pojos.PromethuesNotice;
import com.kuding.properties.enums.ProjectEnviroment;

public class ServiceCheckNotice extends PromethuesNotice {

	private MicroServiceReport servicesReport;

	private int totalServiceCount;

	private int problemServiceCount = 0;

	public ServiceCheckNotice(MicroServiceReport microServiceNotice, int totalServiceCount,
			ProjectEnviroment enviroment, String title) {
		super(title, enviroment);
		this.servicesReport = microServiceNotice;
		this.totalServiceCount = totalServiceCount;
	}

	public boolean needReport() {
		return servicesReport.totalProblemCount() > 0 || servicesReport.getRecoveredServicesInstances().size() > 0;
	}

	public MicroServiceReport getServicesReport() {
		return servicesReport;
	}

	public void setServicesReport(MicroServiceReport servicesReport) {
		this.servicesReport = servicesReport;
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

	public void calculateProblemCount() {
		this.problemServiceCount = servicesReport.getHealthProblems().size()
				+ servicesReport.getInstanceLackProblems().size() + servicesReport.getLackServices().size();
	}

	public String generateText() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("服务监控通知：\n");
		if (servicesReport.getLackServices().size() > 0) {
			stringBuilder.append("缺少微服务：\n");
			servicesReport.getLackServices().forEach(x -> stringBuilder.append(x).append("\n"));
			stringBuilder.append("\n");
		}
		if (servicesReport.getInstanceLackProblems().size() > 0) {
			stringBuilder.append("缺少微服务的实例：\n");
			servicesReport.getInstanceLackProblems().forEach((x, y) -> {
				stringBuilder.append(x).append(":").append(y.getLackCount()).append("\n");
				stringBuilder.append("   已有实例：").append("\n");
				y.getInstanceIds().forEach(z -> stringBuilder.append(z).append("\n"));
			});
		}
		if (servicesReport.getHealthProblems().size() > 0) {
			stringBuilder.append("实例健康检查不通过：\n");
			servicesReport.getHealthProblems().forEach((x, y) -> {
				stringBuilder.append(x).append(":\n");
				y.getHealthyInstances().forEach(z -> {
					stringBuilder.append(z).append("\n");
				});
			});
		}
		if (servicesReport.getRecoveredServicesInstances().size() > 0) {
			stringBuilder.append("有实例恢复：\n");
			servicesReport.getRecoveredServicesInstances().forEach((x, y) -> {
				y.forEach(z -> stringBuilder.append(z).append("\n"));
			});
		}
		stringBuilder.append(createTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		return stringBuilder.toString();
	}
}
