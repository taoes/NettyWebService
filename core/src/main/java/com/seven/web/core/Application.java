package com.seven.web.core;

import com.seven.web.core.common.constant.CommonConstant;
import com.seven.web.core.compose.InitExecutor;
import com.seven.web.core.netty.NettyWebServiceHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

// 提供静态方法启动Web服务
@Slf4j
public class Application {
  /** 初始化 */
  public static void init() throws NoSuchMethodException {
    log.debug("SevenWebService init start.");
    InitExecutor.init();
    log.info("SevenWebService init success!");
  }

  /**
   * 启动Web服务
   *
   * @param args
   */
  public static void start(String[] args) {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();
    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
      serverBootstrap.group(boss, work).channel(NioServerSocketChannel.class).childHandler(new NettyWebServiceHandle());
      ChannelFuture future = serverBootstrap.bind(CommonConstant.SERVICE_PORT).sync();
      log.info("服务启动在:{}", CommonConstant.SERVICE_PORT);
      future.channel().closeFuture().sync();
    } catch (Exception e) {
      log.info("初始化Netty服务发生异常.", e);
    } finally {
      boss.shutdownGracefully();
      work.shutdownGracefully();
    }
  }
}
