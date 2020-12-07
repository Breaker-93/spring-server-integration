# spring服务端开发 常用技术整合模板
 
## 基于springboot 2.2.2 主要集成以下技术：
 - Mybatis Plus 3.1.1
 - Spring Security 5.2.1
 - mysql-connector-java 5.1.46
 - jwt 0.9.1
 - swagger 2.9.2
 
## <span id="top">配置实现了以下主要功能：</span>
 - [√ Spring Security 结合jwt实现授权和认证](#jwt)
 - [ Controller层异常拦截并统一处理](#exception)
 - [√ 配置CORS跨域](#cors)
 - [√ swagger2配置](#swagger)
 - [√ MybatisPlus相关功能配置](#mybatisplus)
    - 分页功能
    - 自动填充
 - [ 接口返回结果封装、统一处理](#mybatisplus)
 - [√ RESTFul Api 设计](#)
 - [√ 日志打印设置](#)
 ### <span id="jwt">✅ 1、Spring Security 结合jwt实现授权和认证</span>
 [▲ 回顶部](#top)
 ### <span id="exception">✅ 2、Controller层异常拦截并统一处理</span>
 [▲ 回顶部](#top)
 ### <span id="cors">✅ 3、配置CORS跨域</span>
 [▲ 回顶部](#top)
 ### <span id="swagger">✅ 4、swagger2配置</span>
 [▲ 回顶部](#top)
## Swagger Api文档访问地址
 启动后：http://localhost:9393/api/v1/doc.html
 
## sql文件说明
 - user.sql 用户登录表（作用户名密码登录使用）
 
 > 最后更新时间：2020.01.25 
  

