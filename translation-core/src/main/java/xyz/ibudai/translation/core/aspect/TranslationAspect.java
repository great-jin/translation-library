package xyz.ibudai.translation.core.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xyz.ibudai.translation.core.TranslateManager;
import xyz.ibudai.translation.core.annotation.EnableTranslation;

import java.util.*;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TranslationAspect {

    @Value("${engine.switch.aop:true}")
    private Boolean enableAop;

    private final TranslateManager translateManager;


    @Pointcut("@annotation(enableTranslation) && @annotation(xyz.ibudai.translation.core.annotation.EnableTranslation)")
    public void pointcut(EnableTranslation enableTranslation) {
    }

    @Around(value = "pointcut(enableTranslation)", argNames = "joinPoint, enableTranslation")
    public Object around(ProceedingJoinPoint joinPoint, EnableTranslation enableTranslation) throws Throwable {
        Object data = joinPoint.proceed();
        if (Boolean.FALSE.equals(enableAop) || Objects.isNull(enableTranslation)) {
            return data;
        }

        try {
            List<Object> targets = Collections.singletonList(data);
            targets = translateManager.fieldTranslate(targets);
            if (CollectionUtils.isEmpty(targets)) {
                return null;
            }
            return targets.get(0);
        } catch (Throwable e) {
            // 失败返回原值
            log.error("TranslationAspect error", e);
            return data;
        }
    }
}
