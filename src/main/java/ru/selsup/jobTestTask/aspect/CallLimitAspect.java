package ru.selsup.jobTestTask.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@RequiredArgsConstructor
@Component
@Slf4j
@EnableScheduling
public class CallLimitAspect {
    private final AtomicInteger resetCounter = new AtomicInteger(0);
    private final int MAX_NUMB_OF_REQUESTS_PER_SECOND = 2;

    @Around("execution(* ru.selsup.jobTestTask.controller.*.*(..))")
    private Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        resetCounter.incrementAndGet();
        log.info("Incoming request to API. request counter in the present: {}", resetCounter.get());
        if (resetCounter.get() > MAX_NUMB_OF_REQUESTS_PER_SECOND){
            log.warn("overflow of the number of calls ({}). Temporary blocking", resetCounter.get());
            while (resetCounter.get() > MAX_NUMB_OF_REQUESTS_PER_SECOND){
                Thread.sleep(100);
            }
        }

        return joinPoint.proceed();
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 1, initialDelay = 0)
    private void resetRequestCounter(){
        if (resetCounter.get() > 0) resetCounter.set(0);
    }
}
