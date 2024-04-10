package com.lionsaid.admin.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {
    String value() default ""; // Used for adding annotation description

    int expired() default 15;   // Used for specifying the number of days as default
}
