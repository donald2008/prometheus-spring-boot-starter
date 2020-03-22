package com.kuding.exceptionhandle.decorated;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.kuding.exceptionhandle.ExceptionHandler;
import com.kuding.exceptionhandle.interfaces.ExceptionNoticeHandlerDecoration;

public class AsyncExceptionNoticeHandler implements ExceptionNoticeHandlerDecoration {

	private final ExceptionHandler exceptionHandler;

	private final ThreadPoolTaskExecutor poolTaskExecutor;

	private final Log logger = LogFactory.getLog(getClass());

	public AsyncExceptionNoticeHandler(ExceptionHandler exceptionHandler, ThreadPoolTaskExecutor poolTaskExecutor) {
		this.exceptionHandler = exceptionHandler;
		this.poolTaskExecutor = poolTaskExecutor;
	}

	@Override
	public void createNotice(RuntimeException exception) {
		poolTaskExecutor.execute(new createNoticeRunnable(exception, null, null));
	}

	@Override
	public void createNotice(RuntimeException ex, String method, Object[] args) {
		poolTaskExecutor.execute(new createNoticeRunnable(ex, method, args));
	}

	@Override
	public void createHttpNotice(RuntimeException exception, String url, Map<String, String> param, String requesBody,
			Map<String, String> headers) {
		poolTaskExecutor.execute(new CreateHttpNoticeRunnable(exception, url, param, requesBody, headers));
	}

	class createNoticeRunnable implements Runnable {

		private RuntimeException exception;

		private String method;

		private Object[] args;

		public createNoticeRunnable(RuntimeException exception, String method, Object[] args) {
			this.exception = exception;
			this.method = method;
			this.args = args;
		}

		@Override
		public void run() {
			try {
//				TimeUnit.SECONDS.sleep(10);
				getExceptionHandler().createNotice(exception, method, args);
			} catch (Exception e) {
				logger.warn("异常通知出错：", e);
			}
		}
	}

	class CreateHttpNoticeRunnable implements Runnable {

		private RuntimeException exception;
		private String url;
		private Map<String, String> param;
		private String requesBody;
		private Map<String, String> headers;

		public CreateHttpNoticeRunnable(RuntimeException exception, String url, Map<String, String> param,
				String requesBody, Map<String, String> headers) {
			this.exception = exception;
			this.url = url;
			this.param = param;
			this.requesBody = requesBody;
			this.headers = headers;
		}

		@Override
		public void run() {
			try {
//				TimeUnit.SECONDS.sleep(10);
				getExceptionHandler().createHttpNotice(exception, url, param, requesBody, headers);
			} catch (Exception e) {
				logger.warn("异常通知出错：", e);
			}
		}
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

}
