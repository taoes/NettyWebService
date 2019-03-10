package com.seven.web.core.common.enums;

import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import java.awt.PageAttributes.MediaType;
import lombok.Getter;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 23:48
 */
public enum RenderType {
  JSON("application/json;charset=UTF-8"),
  XML("text/xml;charset=UTF-8"),
  TEXT("text/plain;charset=UTF-8"),
  HTML("text/html;charset=UTF-8");

  @Getter private String type;

  RenderType(String type) {
    this.type = type;
  }
}
