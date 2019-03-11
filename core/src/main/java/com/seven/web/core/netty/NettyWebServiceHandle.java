package com.seven.web.core.netty;

import com.seven.web.core.compose.context.ControllerContext;
import com.seven.web.core.compose.init.DefaultControllerContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-10 09:30
 */
@Slf4j
public class NettyWebServiceHandle extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {
    ChannelPipeline pipeline = socketChannel.pipeline();

    pipeline.addLast(new HttpServerCodec());
    // 开启Gzip
    pipeline.addLast(new HttpContentCompressor());
    // 增加路由分发
    pipeline.addLast(new ControllerDispatcher());
  }
}
