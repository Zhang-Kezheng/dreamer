package com.zkz.dreamer;

import com.zkz.dreamer.exception.GlobalExceptionRegister;
import com.zkz.dreamer.log.LogAspectRegister;
import com.zkz.dreamer.security.WebSecurityRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({GlobalExceptionRegister.class, WebSecurityRegister.class, LogAspectRegister.class,GeneralBeanRegistrar.class})
public @interface EnableDreamer {
    boolean enableLogAspect() default true;
    boolean enableWebSecurity() default true;
    boolean enableGlobalExceptionHandler() default true;
}
