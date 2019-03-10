package com.seven.web.core.compose.init;

import cn.hutool.core.lang.ClassScaner;
import cn.hutool.core.util.StrUtil;
import com.seven.web.core.annotation.Bean;
import com.seven.web.core.annotation.InitFunction;
import com.seven.web.core.annotation.Order;
import com.seven.web.core.common.constant.CommonConstant;
import com.seven.web.core.compose.context.BeanContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Bean的初始化
 *
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 18:07
 */
@Slf4j
@Order(Integer.MIN_VALUE)
public class DefaultBeanContext implements InitFunction, BeanContext {

  private static DefaultBeanContext defaultBeanContext;

  private static Map<String, Object> beansMap;

  static {
    beansMap = new HashMap<>();
    defaultBeanContext = new DefaultBeanContext();
  }

  private DefaultBeanContext() {}

  public static DefaultBeanContext getInstance() {
    if (defaultBeanContext == null) {
      synchronized (DefaultBeanContext.class) {
        if (defaultBeanContext == null) {
          defaultBeanContext = new DefaultBeanContext();
        }
      }
    }
    return defaultBeanContext;
  }

  @Override
  public Object getBean(String name) {
    return beansMap.get(name);
  }

  @Override
  public <T> T getBean(@NonNull String beanName, Class<T> clazz) {
    Object object = beansMap.get(beanName);
    return object == null ? null : (T) object;
  }

  @Override
  public void init() {

    Set<Class<?>> classes = ClassScaner.scanPackage(CommonConstant.BEAN_SCAN_PACKAGE);
    try {
      for (Class<?> aClass : classes) {
        Bean bean = aClass.getAnnotation(Bean.class);
        if (Objects.isNull(bean)) {
          continue;
        }
        String beanName = StrUtil.isNotBlank(bean.value()) ? bean.value() : aClass.getName();
        if (beansMap.containsKey(beanName)) {
          log.error("Bean对象发现相同的Name={},无法注入", beanName);
          System.exit(402);
        }
        beansMap.put(beanName, aClass.newInstance());
      }
    } catch (Exception e) {
      log.error("初始Bean发生异常", e);
      System.exit(501);
    }
    log.info("初始化Bean对象完成");
  }
}
