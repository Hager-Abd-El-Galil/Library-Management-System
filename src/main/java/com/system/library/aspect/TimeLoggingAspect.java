package com.system.library.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class TimeLoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(TimeLoggingAspect.class);

	@Around(value = "execution(* com.system.library..repository.*.*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

		StringBuilder logMessage = new StringBuilder();
		long startTime = System.currentTimeMillis();

		Object result = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - startTime;

		logMessage.append("\n ======= EXECUTION TIME ======= ").append("\n Signature : ")
				.append(joinPoint.getSignature().getName()).append("\n Arguments : ")
				.append(StringUtils.join(joinPoint.getArgs(), ", ")).append("\n Execution Time : ")
				.append(executionTime).append("\n ============================== ");

		logger.error(logMessage.toString());

		return result;

	}
}
