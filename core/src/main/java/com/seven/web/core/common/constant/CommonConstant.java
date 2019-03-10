package com.seven.web.core.common.constant;

import com.seven.web.core.utils.PropertiesUtils;

/**
 * 通用常量
 *
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 18:15
 */
public class CommonConstant {

  //  系统配置文件路径
  private static final String PROPERTIES_PATH = "/application.properties";

  private static PropertiesUtils properties = PropertiesUtils.getInstance(PROPERTIES_PATH);

  public static Integer SERVICE_PORT = properties.getInteger("application.port", 8080);

  public static String APPLICATION_NAME = properties.getString("application.name");

  public static final String BEAN_SCAN_PACKAGE = properties.getString("application.bean.scan.package");
}
