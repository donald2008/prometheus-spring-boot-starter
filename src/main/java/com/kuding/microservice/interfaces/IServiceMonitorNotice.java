package com.kuding.microservice.interfaces;

import com.kuding.microservice.pojo.MicroServiceNotice;

@FunctionalInterface
public interface IServiceMonitorNotice {

	void notice(MicroServiceNotice microServiceNotice);

}
