package com.jude.common.util.pds.aop;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jude.common.util.pds.annotation.PreventDuplicateSubmit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


/**
 * @author yuzhihang
 * @Description
 * @create 2025-03-20 23:06
 */
@Aspect
@Component
@Slf4j
public class PreventDuplicateSubmitAspect {

    private final Cache<String, Boolean> requestCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS) // 5秒过期
            .build();

    private final HttpServletRequest request;

    public PreventDuplicateSubmitAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Around("@annotation(preventDuplicateSubmit)")
    public Object around(ProceedingJoinPoint joinPoint, PreventDuplicateSubmit preventDuplicateSubmit) throws Throwable {
        String key = getRequestKey();
        if (key == null) {
            return joinPoint.proceed();
        }

        if (requestCache.getIfPresent(key) != null) {
            log.warn("重复提交拦截: {}", key);
            throw new RuntimeException("请勿重复提交！");
        }

        requestCache.put(key, Boolean.TRUE);
        return joinPoint.proceed();
    }

    private String getRequestKey() {
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        return "REQ_LOCK:" + uri + ":" + ip;
    }
}
