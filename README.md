<div align=center>
  <img width="320" src="http://www.zhoutao123.com/picture/book_convert/netty_web.png" alt="图片加载失败"/>
</div>



<div style="height:20px"></div>

您可以通过以下导航来在 语雀 中访问我的读书笔记，涵盖了技术、服务端开发与基础架构、闲谈杂记等多个项目：


《[前端开发杂记](https://www.yuque.com/zhoutao123/front_end)》 
《[设计模式](https://www.yuque.com/zhoutao123/design_pattern)》
《[深入理解JVM虚拟机](https://www.yuque.com/zhoutao123/jvm)》
《[Java 并发编程](https://www.yuque.com/zhoutao123/java_concurrent)》 
《[Netty入门与实战](https://www.yuque.com/zhoutao123/netty)》
《[高性能MySQL](https://www.yuque.com/zhoutao123/mysql)》

 [最新文档，请访问语雀文档](https://www.yuque.com/zhoutao123)

---





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

