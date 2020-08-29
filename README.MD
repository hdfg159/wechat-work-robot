<p align="center">wechat-work-robot - 企业微信机器人工具</p>

# 简介

企业微信机器人工具

# 功能

> 为了更加方便使用企业微信机器人发送相关消息，对此进行简易封装的工具类


支持企业微信机器人发送类型数据

- 文本类型
- markdown类型
- 图片类型
- 图文类型
- 文件类型

# 特点

- 支持链式调用
- 基于 Vert.x 和 RxJava 异步支持
- 易于使用机器人消息构造工具

# 依赖

> 工具基于 Vert.x WebClient 和 RxJava

- vertx-web-client

- vertx-rx-java2

# 使用

使用 `io.github.hdfg159.wechat.work.robot.RobotMessages` 消息工具类构造信息进行发送

发送普通文本消息

```java
Maybe<JsonObject> result = RobotMessages.text("hello world").send("机器人密钥");
```

更多使用案例请查看测试用例 

`io.github.hdfg159.wechat.work.robot.RobotMessagesTest`

# License

wechat-work-robot is under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0) - see the [LICENSE](LICENSE) file for details.