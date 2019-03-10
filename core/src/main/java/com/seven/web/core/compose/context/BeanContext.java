package com.seven.web.core.compose.context;

import lombok.NonNull;

/**
 * Bean的上下文
 *
 * @author Seven zhoutao825638@vip.qq.com
 * @version 0.0.1
 * @data 2019-03-09 19:30
 */
public interface BeanContext {

  /**
   * 使用一个不是NUll<i>beanName</i> 从IOC容器中获取Bean
   *
   * @param beanName
   * @return
   */
  Object getBean(@NonNull String beanName);

  /**
   * 获取一个Bean
   *
   * @param beanName Bean的名称
   * @param clazz Bean的类型
   * @param <T> 泛型类型
   * @return
   */
  <T> T getBean(@NonNull String beanName, Class<T> clazz);
}
