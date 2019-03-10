package com.seven.web.core.common.enums;

import io.netty.handler.codec.http.HttpMethod;
import lombok.Data;
import lombok.Getter;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 23:45
 */
public enum HttpRequestMethod {
  GET(HttpMethod.GET),
  POST(HttpMethod.POST),
  PUT(HttpMethod.PUT),
  PATCH(HttpMethod.PATCH),
  DELETE(HttpMethod.DELETE),
  OPTIONS(HttpMethod.OPTIONS);

  @Getter private HttpMethod httpMethod;

  HttpRequestMethod(HttpMethod httpMethod) {
    this.httpMethod = httpMethod;
  }
}
