package com.seven.web.core.netty;

import com.alibaba.fastjson.JSON;
import com.seven.web.core.common.enums.RenderType;
import com.seven.web.core.compose.ControllerProxyBean;
import com.seven.web.core.compose.ProxyBeanInvocation;
import com.seven.web.core.compose.ServiceContext;
import com.seven.web.core.compose.context.ControllerContext;
import com.seven.web.core.compose.context.ControllerProxyContext;
import com.seven.web.core.compose.init.ControllerProxyContextImpl;
import com.seven.web.core.compose.init.DefaultControllerContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 09:34
 */
@Slf4j
public class ControllerDispatcher extends SimpleChannelInboundHandler<HttpRequest> {

  private static ControllerContext controllerContext = DefaultControllerContext.getInstance();

  private static ControllerProxyContext proxyContext = ControllerProxyContextImpl.getInstance();

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
    // 暂存上下文

    stashRequest(channelHandlerContext, httpRequest);

    // 获取URL
    String uri = httpRequest.uri();
    ControllerProxyBean proxy = proxyContext.getProxy(httpRequest.method(), uri);
    // 如果不存在，则写入404页面
    if (proxy == null) {
      flushNotFoundPage();
      return;
    }
    Object result = null;
    try {
      result = ProxyBeanInvocation.invoke(proxy);
    } catch (Exception e) {
      flushServerErrorPage(e);
      return;
    }

    String data = null;
    if (result instanceof String) {
      data = (String) result;
    } else if (proxy.getRenderType() == RenderType.JSON) {
      data = JSON.toJSONString(result);
    } else {
      // TODO: 搭建模板渲染引擎
      data = "暂不支持模板渲染引擎";
    }
    byte[] bytes = data.getBytes(CharsetUtil.UTF_8);
    ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(bytes);
    HttpResponse response =
        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, wrappedBuffer);
    response.headers().set("Content-Type", proxy.getRenderType().getType());
    response.headers().set("Content-Length", bytes.length);
    channelHandlerContext.writeAndFlush(response);
  }

  /** 写入服务器错误 */
  private void flushServerErrorPage(Exception e) {
    String data = e.getMessage();
    byte[] bytes = data.getBytes(CharsetUtil.UTF_8);
    ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(bytes);
    HttpResponse response =
        new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR, wrappedBuffer);
    response.headers().set("Content-Length", bytes.length);
    ServiceContext.currentContext().getHandlerContext().writeAndFlush(response);
  }

  /** 写入404页 */
  private void flushNotFoundPage() {
    String data = "NOT FOUND PAGE";
    byte[] bytes = data.getBytes(CharsetUtil.UTF_8);
    ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(bytes);
    HttpResponse response =
        new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, wrappedBuffer);
    response.headers().set("Content-Length", bytes.length);
    ServiceContext.currentContext().getHandlerContext().writeAndFlush(response);
  }

  private void stashRequest(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest) {
    ServiceContext.currentContext()
        .setHandlerContext(channelHandlerContext)
        .setHttpRequest(httpRequest);
  }

  @Data
  static class Information {
    String name;

    String uri;

    HttpMethod method;

    int status;
  }
}
