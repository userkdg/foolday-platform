1.目前未考虑把admin-web和wechat-web的权限统一
2.设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
（为了保持模块的依赖合理，不希望web和service的模块被相互依赖倒置）
3.模块说明
```
    <modules>
        <module>platform-admin</module> 后台管理web平台，可以使用service/service-dto/service-job/common模块的代码（可用spring技术）,注尽可能不要在web层调用/注入dao层的接口
        <module>platform-wechat</module> 后台管理web平台，可以使用service/service-dto/service-job/common模块的代码（可用spring技术）,注尽可能不要在web层调用/注入dao层的接口
        <module>platform-schedule-job</module>后台管理web平台，可以使用service/service-dto/service-job/common模块的代码（可用spring技术）,注尽可能不要在web层调用/注入dao层的接口
        <module>platform-service</module> web平台 wechat平台和调度schedule平台的业务模块（可用spring技术）
        <module>platform-service-dto</module>（不可用spring技术，目的为了禁止本模块编写业务逻辑代码，作业为service和web的数据交互传输使用，也可以作为web与前端交互使用） 
        <module>platform-dao</module>抽象层主要与服务器交互，数交互的（可用spring技术）,目前提供了mybatis 和 commons-dbutils的QueryRunner，和spring-data-redis作为抽象层交互工具
        <module>platform-service-api</module>为service模块的带外接口，为了后续做微服务接口对外暴露考虑,同时对
        <module>platform-common</module>（不可用spring技术，目的为了后续把common部分解耦出来通用）
    </modules>
 ```
4.目前技术：spring boot, mybatis, mybatis-plus, commons-dbutils, redis
5.父模块的pom的findbugs可以调整为skip=true 跳过扫bug
6.redis的db选择，目前wechat 的默认配置选择db1 而admin 选择db0 而schedule-job 选择db2

7.具有业务逻辑内容记录：
> platform-admin

> platform-wechat

> platform-schedule-job

> platform-service

> platform-service-dto

> platform-dao

> platform-service-api

> platform-common
