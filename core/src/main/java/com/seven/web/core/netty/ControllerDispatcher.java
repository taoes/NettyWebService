package com.seven.web.core.netty;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.seven.web.core.common.constant.CommonConstant;
import com.seven.web.core.common.enums.ContentType;
import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.compose.context.ControllerContext;
import com.seven.web.core.compose.ServiceContext;
import com.seven.web.core.compose.init.DefaultControllerContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
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

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpRequest httpRequest)
      throws Exception {
    // 暂存上下文

    stashRequest(channelHandlerContext, httpRequest);

    String date =
        "<h1 color=red>This is a text/html resources</h1> <ul><li>1</li><li>1</li><li>1</li><li>1</li><li>1</li></ul>";

    byte[] bytes = date.getBytes(CharsetUtil.UTF_8);
    ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(bytes);
    HttpResponse response =
        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, wrappedBuffer);
    response.headers().set("Content-Type", ContentType.APPLICATION_HTML);
    response.headers().set("Content-Length", bytes.length);
    channelHandlerContext.writeAndFlush(response);
    log.debug("接收到请求信息.....");
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
