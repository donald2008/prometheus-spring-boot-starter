package com.kuding.microservice.properties;

import java.time.Duration;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exceptionnotice.micro-service")
public class ServiceMonitorProperties {

	/**
	 * 需要监控的服务
	 */
	private Map<String, ServiceCheck> monitorServices;

	/**
	 * 微服务存在性检查间隔
	 */
	private Duration serviceExistCheckInterval = Duration.ofMinutes(2);

	/**
	 * 服务检查通知的时间间隔
	 */
	private Duration serviceCheckNoticeInterval = Duration.ofMinutes(3);

	/**
	 * 是否开启服务检查
	 */
	private boolean enabled;

	/**
	 * 是否自动发现服务
	 */
	private boolean autoDiscovery;

	/**
	 * 微服务的部署环境
	 */
	private MonitorEnviroment monitorEnviroment;

	public Map<String, ServiceCheck> getMonitorServices() {
		return monitorServices;
	}

	public void setMonitorServices(Map<String, ServiceCheck> monitorServices) {
		this.monitorServices = monitorServices;
	}

	public Duration getServiceExistCheckInterval() {
		return serviceExistCheckInterval;
	}

	public void setServiceExistCheckInterval(Duration serviceExistCheckInterval) {
		this.serviceExistCheckInterval = serviceExistCheckInterval;
	}

	public Duration getServiceCheckNoticeInterval() {
		return serviceCheckNoticeInterval;
	}

	public void setServiceCheckNoticeInterval(Duration serviceCheckNoticeInterval) {
		this.serviceCheckNoticeInterval = serviceCheckNoticeInterval;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAutoDiscovery() {
		return autoDiscovery;
	}

	public void setAutoDiscovery(boolean autoDiscovery) {
		this.autoDiscovery = autoDiscovery;
	}

	public MonitorEnviroment getMonitorEnviroment() {
		return monitorEnviroment;
	}

	public void setMonitorEnviroment(MonitorEnviroment monitorEnviroment) {
		this.monitorEnviroment = monitorEnviroment;
	}

}
