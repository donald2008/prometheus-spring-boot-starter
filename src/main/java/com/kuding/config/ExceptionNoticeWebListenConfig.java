package com.kuding.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.kuding.exceptionhandle.interfaces.ExceptionNoticeHandlerDecoration;
import com.kuding.properties.ExceptionNoticeProperty;
import com.kuding.web.ClearBodyInterceptor;
import com.kuding.web.CurrentRequestHeaderResolver;
import com.kuding.web.CurrentRequetBodyResolver;
import com.kuding.web.DefaultRequestBodyResolver;
import com.kuding.web.DefaultRequestHeaderResolver;
import com.kuding.web.ExceptionNoticeHandlerResolver;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "exceptionnotice.listen-type", havingValue = "web")
public class ExceptionNoticeWebListenConfig implements WebMvcConfigurer, WebMvcRegistrations {

	@Autowired
	private ExceptionNoticeHandlerDecoration exceptionHandler;
	@Autowired
	private ExceptionNoticeProperty exceptionNoticeProperty;

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		resolvers.add(0, ExceptionNoticeHandlerResolver());
	}

	@Bean
	public ExceptionNoticeHandlerResolver ExceptionNoticeHandlerResolver() {
		ExceptionNoticeHandlerResolver exceptionNoticeResolver = new ExceptionNoticeHandlerResolver(exceptionHandler,
				currentRequetBodyResolver(), currentRequestHeaderResolver(), exceptionNoticeProperty);
		return exceptionNoticeResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(clearBodyInterceptor());
	}

	@Bean
	public ClearBodyInterceptor clearBodyInterceptor() {
		ClearBodyInterceptor bodyInterceptor = new ClearBodyInterceptor(currentRequetBodyResolver());
		return bodyInterceptor;

	}

	@Bean
	@ConditionalOnMissingBean(value = CurrentRequestHeaderResolver.class)
	public CurrentRequestHeaderResolver currentRequestHeaderResolver() {
		return new DefaultRequestHeaderResolver();
	}

	@Bean
	public CurrentRequetBodyResolver currentRequetBodyResolver() {
		return new DefaultRequestBodyResolver();
	}

	@Override
	public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
		adapter.setRequestBodyAdvice(Arrays.asList(currentRequetBodyResolver()));
		return adapter;
	}

}
