package com.kuding.microservice.properties;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCheck {

	/**
	 * 开启检查
	 */
	private boolean enabled = true;

	/**
	 * 服务器数量
	 */
	private int serviceCount = 1;

	/**
	 * 微服务健康检查地址
	 */
	private String healthCheckUrl = "/actuator/health";

	/**
	 * 微服务检查的header信息
	 */
	private Map<String, List<String>> healthCheckHeaders = new HashMap<>();

	/**
	 * 服务实例检查间隔
	 */
	private Duration checkInterval = Duration.ofMinutes(1);

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}

	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
	}

	public Map<String, List<String>> getHealthCheckHeaders() {
		return healthCheckHeaders;
	}

	public void setHealthCheckHeaders(Map<String, List<String>> healthCheckHeaders) {
		this.healthCheckHeaders = healthCheckHeaders;
	}

	public Duration getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(Duration checkInterval) {
		this.checkInterval = checkInterval;
	}

	@Override
	public String toString() {
		return "ServiceCheck [enabled=" + enabled + ", serviceCount=" + serviceCount + ", healthCheckUrl="
				+ healthCheckUrl + ", healthCheckHeaders=" + healthCheckHeaders + ", checkInterval=" + checkInterval
				+ "]";
	}

}
