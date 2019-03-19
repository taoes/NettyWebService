package com.seven.web.core.compose.context;

import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.compose.ControllerProxyBean;
import io.netty.handler.codec.http.HttpMethod;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 12:06
 */
public interface ControllerProxyContext {

  void addProxy(String path, ControllerProxyBean controllerProxy);

  ControllerProxyBean getProxy(HttpMethod method, String uri);
}
