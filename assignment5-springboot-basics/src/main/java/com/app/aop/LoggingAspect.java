package com.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Pointcut("execution(* com.app.service.*.*(..))")
	public void serviceMethods() {
	}

	@Pointcut("execution(* com.app.service.BookService.getBook(..))")
	public void getBookMethod() {
	}

	@Before("serviceMethods()")
	public void logBefore(JoinPoint jp) {
		logger.info("Before method: {}", jp.getSignature());
	}

	@After("serviceMethods()")
	public void logAfter(JoinPoint jp) {
		logger.info("After method: {}", jp.getSignature());
	}

	@AfterReturning(pointcut = "getBookMethod()", returning = "result")
	public void logReturn(Object result) {
		logger.info("Method returned: {}", result);
	}

	@AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
	public void logException(Exception ex) {
		logger.error("Exception thrown: {}", ex.getMessage(), ex);
	}

	@Around("serviceMethods()")
	public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();

		Object result;
		try {
			result = pjp.proceed();
		} catch (Throwable ex) {
			long endEx = System.currentTimeMillis();
			logger.error("Execution Time (with exception) of {}: {}ms", pjp.getSignature(), (endEx - start));
			throw ex;
		}

		long end = System.currentTimeMillis();
		logger.info("Execution Time of {}: {}ms", pjp.getSignature(), (end - start));

		return result;
	}
}
