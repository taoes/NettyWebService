package com.seven.web.core.compose.init;

import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import com.seven.web.core.annotation.Controller;
import com.seven.web.core.annotation.InitFunction;
import com.seven.web.core.annotation.Mapping;
import com.seven.web.core.annotation.Order;
import com.seven.web.core.common.constant.CommonConstant;
import com.seven.web.core.common.enums.RenderType;
import io.netty.handler.codec.http.HttpMethod;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller初始化
 *
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 18:07
 */
@Order(value = 1)
@Slf4j
public class DefaultControllerContext implements InitFunction {

  private static DefaultBeanContext beanContext;

  private static volatile DefaultControllerContext controllerContext;

  public final String SPACING_FLAG = "/";

  @Override
  public void init() {
    log.info("开始初始化Controller对象");
    synchronized (DefaultControllerContext.class) {
      beanContext = DefaultBeanContext.getInstance();
      controllerContext = DefaultControllerContext.getInstance();
      Set<Class<?>> classes =
          ClassScaner.scanPackageByAnnotation(CommonConstant.BEAN_SCAN_PACKAGE, Controller.class);
      if (classes.isEmpty()) {
        log.info("未发现Controller对象,停止注入");
        return;
      }
      for (Class<?> aClass : classes) {
        Controller controller = aClass.getAnnotation(Controller.class);
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
          Mapping mapping = method.getAnnotation(Mapping.class);
          if (Objects.nonNull(mapping)) {
            addRoute(controller, mapping);
          }
        }
      }
    }
  }

  private void addRoute(Controller controller, Mapping mapping) {
    StringBuffer routerBuffer = new StringBuffer(controller.value());
    if (StrUtil.isNotBlank(mapping.value())) {
      if (!mapping.value().startsWith(SPACING_FLAG)) {
        routerBuffer.append(SPACING_FLAG);
      }
      routerBuffer.append(mapping.value());
    }
    HttpMethod method = mapping.method().getHttpMethod();
    RenderType renderType = mapping.renderType();
    // TODO: 继续完成路由注入
    log.info("注入路由:[{}] {}", method.name(), routerBuffer.toString());
  }

  public static DefaultControllerContext getInstance() {
    if (controllerContext == null) {
      synchronized (DefaultControllerContext.class) {
        if (controllerContext == null) {
          controllerContext = new DefaultControllerContext();
        }
      }
    }
    return controllerContext;
  }
}
