package com.seven.web.core.compose;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.util.concurrent.FastThreadLocal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 09:43
 */
@Slf4j
@Data
@Accessors(chain = true)
public class ServiceContext {

  private static final FastThreadLocal<ServiceContext> CONTEXT_HANDLE = new FastThreadLocal<>();

  private HttpRequest httpRequest;

  private HttpResponse httpResponse;

  private ChannelHandlerContext handlerContext;

  private Set<Cookie> cookies;

  private ServiceContext() {}

  public ServiceContext addCookie(Cookie cookie) {
    if (Objects.nonNull(cookie)) {
      if (Objects.isNull(cookies)) {
        cookies = new HashSet<>();
      }
      cookies.add(cookie);
    }
    return this;
  }

  public static ServiceContext currentContext() {
    ServiceContext context = CONTEXT_HANDLE.get();
    if (context == null) {
      context = new ServiceContext();
      CONTEXT_HANDLE.set(context);
    }
    return context;
  }

  public static void clean() {
    CONTEXT_HANDLE.remove();
  }
}
