package com.example.petswithai.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.petswithai.controller..*(..)) || execution(* com.example.petswithai.service..*(..)) || execution(* com.example.petswithai.repository..*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        if (logger.isDebugEnabled()) {
            logger.debug("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.petswithai.controller..*(..)) || execution(* com.example.petswithai.service..*(..)) || execution(* com.example.petswithai.repository..*(..))", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting method: {} with result: {}", joinPoint.getSignature(), result);
        }
    }
}