package com.seven.web.core.compose.init;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.compose.ControllerProxy;
import com.seven.web.core.compose.context.ControllerProxyContext;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 代理对象上下文
 *
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 12:07
 */
@Slf4j
public class ControllerProxyContextImpl implements ControllerProxyContext {

  private static Map<String, ControllerProxy> proxyMap;

  static {
    proxyMap = new HashMap<>();
  }

  private ControllerProxyContextImpl() {}


  private static ControllerProxyContext proxyContextContext;

  public static ControllerProxyContext getInstance() {
    if (proxyContextContext == null) {
      synchronized (ControllerProxyContextImpl.class) {
        if (proxyContextContext == null) {
          proxyContextContext = new ControllerProxyContextImpl();
        }
      }
    }
    return proxyContextContext;
  }

  @Override
  public void addProxy(String path, ControllerProxy controllerProxy) {
    proxyMap.putIfAbsent(path,controllerProxy);
  }

  @Override
  public ControllerProxy getProxy(HttpRequestMethod method, String uri) {


    return null;
  }
}
