package com.seven.web.example.controller;

import com.seven.web.core.annotation.Bean;
import com.seven.web.core.annotation.Controller;
import com.seven.web.core.annotation.Mapping;
import com.seven.web.core.common.enums.HttpRequestMethod;
import com.seven.web.core.common.enums.RenderType;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 23:51
 */
@Bean
@Controller("/index")
public class IndexController {

  @Mapping(method = HttpRequestMethod.GET, renderType = RenderType.HTML)
  public Integer getIndexPage() {
    return 12134;
  }

  @Mapping(value = "/123", method = HttpRequestMethod.GET, renderType = RenderType.JSON)
  public String renderJsonString() {
    return "人生若只如初见,何事悲风秋画扇";
  }
}
