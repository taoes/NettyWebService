package com.seven.web.core;

import com.seven.web.core.compose.InitExecutor;
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
  public static void start(String[] args) {}
}
