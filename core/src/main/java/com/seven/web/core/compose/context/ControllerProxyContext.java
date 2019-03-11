package com.seven.web.core.compose.context;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.compose.ControllerProxy;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 12:06
 */
public interface ControllerProxyContext {

  void addProxy(String path, ControllerProxy controllerProxy);

  ControllerProxy getProxy(HttpRequestMethod method, String uri);
}
