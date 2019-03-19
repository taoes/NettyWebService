package com.seven.web.core.compose.init;

import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import com.seven.web.core.annotation.Controller;
import com.seven.web.core.annotation.InitFunction;
import com.seven.web.core.annotation.Mapping;
import com.seven.web.core.annotation.Order;
import com.seven.web.core.common.constant.CommonConstant;
import com.seven.web.core.common.enums.RenderType;
import com.seven.web.core.compose.ControllerProxyBean;
import com.seven.web.core.compose.context.ControllerContext;
import com.seven.web.core.compose.context.ControllerProxyContext;
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
public class DefaultControllerContext implements InitFunction, ControllerContext {

  private static DefaultBeanContext beanContext;

  private static volatile DefaultControllerContext controllerContext;

  private static ControllerProxyContext controllerProxyContext;

  public final String SPACING_FLAG = "/";

  @Override
  public void init() {
    log.info("开始初始化Controller对象");
    synchronized (DefaultControllerContext.class) {
      beanContext = DefaultBeanContext.getInstance();
      controllerContext = DefaultControllerContext.getInstance();
      controllerProxyContext = ControllerProxyContextImpl.getInstance();
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
            addProxy(aClass, method, controller, mapping);
          }
        }
      }
    }
  }

  /** 增加代理对象信息 */
  private void addProxy(Class<?> clazz, Method method, Controller controller, Mapping mapping) {

    String url = getURL(controller, mapping);

    try {

      // 构建服务对象开始注入
      ControllerProxyBean proxyBean = new ControllerProxyBean();
      proxyBean.setController(controller);
      proxyBean.setMethod(method);
      proxyBean.setMethodName(method.getName());
      proxyBean.setRenderType(mapping.renderType());
      proxyBean.setRequestMethod(mapping.method());
      proxyBean.setProxyInstance(clazz.newInstance());
      controllerProxyContext.addProxy(url, proxyBean);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  private void addRoute(Controller controller, Mapping mapping) {
    String url = getURL(controller, mapping);
    HttpMethod method = mapping.method().getHttpMethod();
    RenderType renderType = mapping.renderType();
    // TODO: 继续完成路由注入
    log.info("注入路由:[{}] {}", method.name(), url);
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

  private String getURL(Controller controller, Mapping mapping) {
    StringBuffer routerBuffer = new StringBuffer(controller.value());
    if (StrUtil.isNotBlank(mapping.value())) {
      if (!mapping.value().startsWith(SPACING_FLAG)) {
        routerBuffer.append(SPACING_FLAG);
      }
      routerBuffer.append(mapping.value());
    }
    return routerBuffer.toString();
  }
}
