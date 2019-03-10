package com.seven.web.core.compose.init;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.compose.ControllerProxy;
import com.seven.web.core.compose.context.ControllerProxyContext;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 12:07
 */
public class ControllerProxyContextImpl implements ControllerProxyContext {

  @Override
  public void addProxy(String path, ControllerProxy controllerProxy) {

  }

  @Override
  public ControllerProxy getProxy(HttpRequestMethod method, String uri) {
    return null;
  }
}
