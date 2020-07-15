package com.kuding.microservice.properties;

public enum MonitorEnviroment {

	DEV("dev"), TEST("test"), PRO("pro");

	private final String value;

	private MonitorEnviroment(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
