package ru.starter_t1.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Level logLevel;

    public LoggingAspect(Level logLevel) {
        this.logLevel = logLevel;
    }

    @Before("@annotation(ru.starter_t1.aspect.annotation.LogExecution)")
    public void loggingBefore(JoinPoint joinPoint) {
        LOGGER.log(logLevel, String.format("start method %s with parameter: %s",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())));
    }

    @AfterThrowing(
            pointcut = "@annotation(ru.starter_t1.aspect.annotation.LogException)",
            throwing = "exception"
    )
    public void loggingAfterThrowing(JoinPoint joinPoint, RuntimeException exception) {
        LOGGER.log(logLevel, String.format("Exception in method - %s", joinPoint.getSignature().getName()));
        LOGGER.log(logLevel, String.format("Exception type is - %s and message - %s",
                exception.getClass().getSimpleName(),
                exception.getMessage()));
    }

    @AfterReturning(
            pointcut = "@annotation(ru.starter_t1.aspect.annotation.HandlingResult)",
            returning = "result"
    )
    public void handlingResult(JoinPoint joinPoint, Object result) {
        LOGGER.log(logLevel, String.format("Method was calling - %s", joinPoint.getSignature().getName()));
        LOGGER.log(logLevel, String.format("Result is: %s", result.toString()));
    }

    @Around("@annotation(ru.starter_t1.aspect.annotation.LogTracking)")
    public Object loggingAround(ProceedingJoinPoint joinPoint) {
        LOGGER.log(logLevel, String.format("Method %s START", joinPoint.getSignature().getName()));

        Object proceeded;

        long start = System.currentTimeMillis();

        try {
            proceeded = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException("Aspect Around broken");
        }

        long end = System.currentTimeMillis();

        LOGGER.log(logLevel, String.format("Method %s END", joinPoint.getSignature().getName()));
        LOGGER.log(logLevel, String.format("The method %s worked in about: %dms",
                joinPoint.getSignature().getName(),
                end - start));
        return proceeded;
    }
}
