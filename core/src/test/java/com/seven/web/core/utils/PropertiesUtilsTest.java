package com.seven.web.core.utils;

import com.seven.web.core.common.constant.CommonConstant;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 18:40
 */
public class PropertiesUtilsTest {

  @Test
  public void getInstance() {
    PropertiesUtils utils = PropertiesUtils.getInstance("/application.properties");
    Assert.assertEquals(CommonConstant.APPLICATION_NAME, utils.getString("application.name"));
    Assert.assertEquals(CommonConstant.SERVICE_PORT, utils.getInteger("application.port", 8080));
  }
}
