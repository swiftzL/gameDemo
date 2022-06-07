package com.zl.server.netty.anno;


import com.zl.server.commons.Command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zl.server.commons.Constants.NORMAL_COMMAND;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NetMessageInvoke {
    Command value();

    int commandType() default NORMAL_COMMAND;
}
