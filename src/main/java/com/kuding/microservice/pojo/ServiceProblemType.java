package com.kuding.microservice.pojo;

public enum ServiceProblemType {

	NOT_FOUND("服务缺失"), INSTANCE_LACK("缺少服务实例（服务实例不够）"), INSTANCE_NOT_HEALTHY("服务健康问题");

	private final String explain;

	private ServiceProblemType(String explain) {
		this.explain = explain;
	}

	public String getExplain() {
		return explain;
	}

}
