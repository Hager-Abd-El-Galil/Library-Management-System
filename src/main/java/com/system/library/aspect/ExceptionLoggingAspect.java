package com.system.library.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class ExceptionLoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

	@AfterThrowing(pointcut = "execution(* com.system.library..service.*.*(..))", throwing = "exception")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {

		StringBuilder logMessage = new StringBuilder();

		logMessage.append("\n ======= EXCEPTION ======= ").append("\n Signature : ")
				.append(joinPoint.getSignature().getName()).append("\n Arguments : ")
				.append(StringUtils.join(joinPoint.getArgs(), ", ")).append("\n Exception : ")
				.append(exception.getClass().getSimpleName()).append("\n Message : ").append(exception.getMessage())
				.append("\n =========================");

		logger.error(logMessage.toString());
	}
}
