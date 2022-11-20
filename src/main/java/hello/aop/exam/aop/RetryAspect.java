package hello.aop.exam.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxCount = retry.value();
        Exception exceptionHolder = null;

        for(int retryCount = 1; retryCount <= maxCount; retryCount++) {
            try {
                log.info("[count] retry / max = {} / {}", retryCount, maxCount);
                return joinPoint.proceed();
            } catch(Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}
