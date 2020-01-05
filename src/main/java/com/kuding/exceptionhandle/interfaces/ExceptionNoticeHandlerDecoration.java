package com.kuding.exceptionhandle.interfaces;

import java.util.Map;

import com.kuding.exceptionhandle.ExceptionHandler;

public interface ExceptionNoticeHandlerDecoration {

	public ExceptionHandler getExceptionHandler();

	/**
	 * 最基础的异常通知的创建方法
	 * 
	 * @param blamedFor 谁背锅？
	 * @param exception 异常信息
	 * 
	 * @return
	 */
	default public void createNotice(RuntimeException exception) {
		getExceptionHandler().createNotice(exception);
	}

	/**
	 * 反射方式获取方法中出现的异常进行的通知
	 * 
	 * @param blamedFor 谁背锅？
	 * @param ex        异常信息
	 * @param method    方法名
	 * @param args      参数信息
	 * @return
	 */
	default public void createNotice(RuntimeException ex, String method, Object[] args) {
		getExceptionHandler().createNotice(ex, method, args);
	}

	/**
	 * 创建一个http请求异常的通知
	 * 
	 * @param blamedFor
	 * @param exception
	 * @param url
	 * @param param
	 * @param requesBody
	 * @param headers
	 * @return
	 */
	default public void createHttpNotice(RuntimeException exception, String url, Map<String, String> param,
			String requesBody, Map<String, String> headers) {
		getExceptionHandler().createHttpNotice(exception, url, param, requesBody, headers);
	}

}
