package com.seven.web.core.annotation;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.common.enums.RenderType;
import io.netty.handler.codec.http.HttpMethod;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 19:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

  String value() default "";

  HttpRequestMethod method() default HttpRequestMethod.GET;

  RenderType renderType() default RenderType.JSON;
}
