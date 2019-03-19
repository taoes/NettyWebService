package com.seven.web.core.compose;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/** 执行代理对象的指定方法 */
public class ProxyBeanInvocation {

  public static Object invoke(ControllerProxyBean proxyBean) throws Exception {
    Object instance = proxyBean.getProxyInstance();
    Method method = proxyBean.getMethod();
    String methodName = proxyBean.getMethodName();
    return invoke(instance, method, methodName);
  }

  public static Object invoke(Object proxyInstance, Method method, String methodName)
      throws InvocationTargetException {
    Class clazz = proxyInstance.getClass();
    Class<?>[] parameterTypes = method.getParameterTypes();
    // 使用 CGLib 执行反射调用
    FastClass fastClass = FastClass.create(clazz);
    FastMethod fastMethod = fastClass.getMethod(methodName, parameterTypes);
    // 调用，并得到调用结果
    Object result = fastMethod.invoke(proxyInstance, new Object[] {});
    return result;
  }
}
