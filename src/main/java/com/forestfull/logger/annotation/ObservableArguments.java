package com.forestfull.logger.annotation;


import com.forestfull.logger.Level;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ObservableArguments {
    Level level() default Level.ALL;
}
