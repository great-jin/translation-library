package xyz.ibudai.translation.core.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xyz.ibudai.translation.core.TranslateManager;

import java.util.*;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TranslationAspect {

    private final TranslateManager translateManager;


    @Pointcut("execution (public * xyz.ibudai.translation.web.service.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            Object data = joinPoint.proceed();
            List<Object> targets = Collections.singletonList(data);
            targets = translateManager.fieldTranslate(targets);
            if (CollectionUtils.isEmpty(targets)) {
                return null;
            }

            return targets.get(0);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
