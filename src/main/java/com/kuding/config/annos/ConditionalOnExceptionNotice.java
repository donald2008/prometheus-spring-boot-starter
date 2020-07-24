package com.kuding.config.annos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

import com.kuding.config.conditions.OnExceptionNoticeContition;
import com.kuding.config.conditions.PrometheusEnabledCondition;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({ PrometheusEnabledCondition.class, OnExceptionNoticeContition.class })
public @interface ConditionalOnExceptionNotice {

}
