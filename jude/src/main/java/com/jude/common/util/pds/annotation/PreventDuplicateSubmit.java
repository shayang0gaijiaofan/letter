package com.jude.common.util.pds.annotation;
import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PreventDuplicateSubmit {
    long expireTime() default 5; // 默认5秒内不可重复提交
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
