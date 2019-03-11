package com.seven.web.core.compose;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.common.enums.RenderType;
import java.lang.reflect.Method;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 12:02
 */
@Data
@Slf4j
public class ControllerProxy {

  private RenderType renderType;

  private HttpRequestMethod requestMethod;

  private Object controller;

  private Method method;

  private String methodName;
}
