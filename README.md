



项目使用Netty实现的一个简单的JavaWeb框架，一个轻量级的框架

## 特性

- [x] IOC 容器
- [x] Netty自定义路由
- [ ] 前/后置拦截器
- [ ] AOP 切面编程支持
- [ ] 异常拦截器
- [ ] 依赖注入
- [x] 接口对象自动序列化JSON
- [x] 404/5XX 异常处理
- [ ] 模板渲染引擎
- [ ] Web层统一异常处理



## 启动方式

1. 打开Moudle example

```java
  public static void main(String[] args) throws NoSuchMethodException {
    Application.init();
    Application.start(args);
  }
```

2. 配置Moudle example 下的resources/application.properties 文件

```properties
application.name=测试案例服务器
application.port=8081
application.bean.scan.package=com.seven.web.example
```

3. 运行步骤1的Main方法,可以观察到已经发现Module中的example的URI

```tex
10:29:56.908 [main] INFO com.seven.web.core.compose.init.DefaultControllerContext - 注入路由:[GET] /user/tao
10:29:56.909 [main] INFO com.seven.web.core.compose.init.DefaultControllerContext - 注入路由:[POST] /user/tom
10:29:56.909 [main] INFO com.seven.web.core.compose.init.DefaultControllerContext - 注入路由:[GET] /index
10:29:56.909 [main] INFO com.seven.web.core.compose.init.DefaultControllerContext - 注入路由:[GET] /index/123
10:29:56.909 [main] INFO com.seven.web.core.Application - SevenWebService init success!
```

