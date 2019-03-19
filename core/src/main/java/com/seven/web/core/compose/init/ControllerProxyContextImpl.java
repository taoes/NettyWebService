package com.seven.web.core.compose.init;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.compose.ControllerProxyBean;
import com.seven.web.core.compose.context.ControllerProxyContext;
import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.HttpMethod;
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

  private static Map<String, ControllerProxyBean> proxyMap;

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
  public void addProxy(String path, ControllerProxyBean controllerProxy) {
    proxyMap.putIfAbsent(path, controllerProxy);
  }

  @Override
  public ControllerProxyBean getProxy(HttpMethod method, String uri) {
    ControllerProxyBean proxyBean = proxyMap.getOrDefault(uri, null);
    if (proxyBean == null) {
      // TODO: 返回一个404 的Bean执行对象
      return null;
    }
    return proxyBean;
  }
}
