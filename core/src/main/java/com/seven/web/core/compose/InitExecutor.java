package com.seven.web.core.compose;

import cn.hutool.core.util.ClassUtil;
import com.seven.web.core.annotation.Bean;
import com.seven.web.core.annotation.InitFunction;
import com.seven.web.core.annotation.Order;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

/**
 * 执行初始化对象
 *
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 18:02
 */
@Slf4j
public class InitExecutor {

  public static void init() throws NoSuchMethodException {
    Set<Class<?>> classes = ClassUtil.scanPackageBySuper("com.seven.web", InitFunction.class);
    if (classes.isEmpty()) {
      return;
    }
    try {
      List<InitBeanWrapper> wrapperList = new ArrayList<>(classes.size() + 1);
      for (Class<?> aClass : classes) {
        if (!aClass.isInterface() && InitFunction.class.isAssignableFrom(aClass)) {
          Constructor<?> constructor = aClass.getDeclaredConstructor();
          constructor.setAccessible(true);
          InitFunction initFunction = (InitFunction) constructor.newInstance();
          insertToList(wrapperList, initFunction);
        }
      }
      wrapperList.stream().map(InitBeanWrapper::getInitFunction).forEach(InitFunction::init);
    } catch (Exception e) {
      log.error("初始化InitFunction发生异常信息:{}", e);
      System.exit(501);
    }
  }

  private static void insertToList(List<InitBeanWrapper> wrapperList, InitFunction initFunction) {
    int order = resolverOrder(initFunction);
    int index = 0;
    for (; index < wrapperList.size(); index++) {
      if (wrapperList.get(index).getOrder() > order) {
        break;
      }
    }
    wrapperList.add(index, new InitBeanWrapper(order, initFunction));
  }

  private static int resolverOrder(InitFunction initFunction) {
    Order order = initFunction.getClass().getAnnotation(Order.class);
    if (Objects.nonNull(order)) {
      return order.value();
    }
    return Integer.MAX_VALUE;
  }

  static class InitBeanWrapper {

    private int order;

    private InitFunction initFunction;

    public InitBeanWrapper(int order, InitFunction initFunction) {
      this.order = order;
      this.initFunction = initFunction;
    }

    public int getOrder() {
      return order;
    }

    public InitFunction getInitFunction() {
      return initFunction;
    }
  }
}
