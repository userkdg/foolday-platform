1. 目前未考虑把admin-web和wechat-web的权限统一
2. 目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
（为了保持模块的依赖合理，不希望web和service的模块被相互依赖倒置）
3. 模块说明
```
    <modules>
        <module>platform-admin</module> 后台管理web平台，可以使用service/service-dto/service-job/common模块的代码（可用spring技术）,注尽可能不要在web层调用/注入dao层的接口
        <module>platform-wechat</module> 后台管理web平台，可以使用service/service-dto/service-job/common模块的代码（可用spring技术）,注尽可能不要在web层调用/注入dao层的接口
        <module>platform-schedule-job</module>后台管理web平台，可以使用service/service-dto/service-job/common模块的代码（可用spring技术）,注尽可能不要在web层调用/注入dao层的接口
        <module>platform-service</module> web平台 wechat平台和调度schedule平台的业务模块（可用spring技术）
        <module>platform-service-dto</module>（不可用spring技术，目的为了禁止本模块编写业务逻辑代码，作业为service和web的数据交互传输使用，也可以作为web与前端交互使用） 
        <module>platform-dao</module>抽象层主要与服务器交互，数交互的（可用spring技术）,目前提供了mybatis 和 commons-dbutils的QueryRunner，和spring-data-redis作为抽象层交互工具
        <module>platform-service-api</module>为service模块的带外接口，为了后续做微服务接口对外暴露考虑,同时对
        <module>platform-common</module>（不可用spring容器管理技术，目的为了后续把common部分解耦出来通用）
    </modules>
 ```
4. 目前技术：spring boot, mybatis, mybatis-plus, commons-dbutils, redis
5. 父模块的pom的findbugs可以调整为skip=true 跳过扫bug
6. redis的db选择，目前wechat 的默认配置选择db1 而admin 选择db0 而schedule-job 选择db2

7.具有业务逻辑内容记录：
> platform-admin

> platform-wechat

> platform-schedule-job

> platform-service

> platform-service-dto

> platform-dao

> platform-service-api

> platform-common

> 原型图：https://org.modao.cc/app/49c52d76b51943debac1fa09665309d79633f94a#screen=s8fb58902046f5777633e87
***
### 2019年8月14日
####后端开发进度方面
1. 项目搭建和表设计已完成，通过git进行项目管理和开发
2. 目前在开发模块
 - 店铺管理（100% 完成店铺crud和地址管理)
 - 桌位管理(100%)
 - 商品模块(100%)
 - 订单管理(100%)
 - 优惠券管理(100%)
 - 后台登录(100%)
 - 图片管理(100% 完成了图片的上传下载和定制图片大小,查看图片等)
 - banner管理(100%)
 - 轮播管理(100%)
 - 文章管理(100%)
 - 意见反馈管理(100%)

####后台架构方面
1. 基础框架：spring boot2.x(spring mvc, spring,...),mybatis(orm),commons-dbutils(jdbc)
2. 持久化：mysql
3. 缓存：ehcache(本地缓存),redis(分布式缓存)
4. 项目管理用github，项目搭建基于maven管理
5. ...后续小程序sdk工具等
####服务器方面
1. 目前搭建了mysql数据库,其他用本地服务器
2....

####小程序方面
1. 目前设定先基本完成后台管理系统，再开发
2. ...

### swagger2 访问 
 1. admin-web : http://localhost/system/swagger-ui.html
 2. wechat-web : http://localhost:8080/wehat/swagger-ui.html
 
### 权限管理
1. 用户选择角色
2. 角色对应权限
3. 权限的控制通过方法级别的url+http method
 * 通过Spring AOP + 注解创建对应的模块的url信息-初始化已有的url信息
 * 启动系统初始化url信息和用户对应的角色和url权限信息
 * 针对已有的权限信息，在用户发起请求的时候进行用户校验和权限验证，拦截器请求，成功则继续，失败则跳到403权限失败
