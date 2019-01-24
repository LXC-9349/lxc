## springbootdemo 后台

##系统环境
### maven+jdk1.8+redis+mysql5.7

## 项目linux启动方式
###  部署方式 （'/opt/springbootdemo/'可以修改，但必须对应在系统中也作同样修改）
#### 建立目录/opt/springbootdemo/springbootdemo  项目的src和pom.xml文件打包解压至目录/springbootdemo/springbootdemo/
#### 将springboot-prod.sh 拷贝至/opt/springbootdemo/
###  springboot-prod.sh使用方法
#### 脚本方法 ：restart重启 start 启动 log实时日志 stop停止服务 deploy一键部署
#### 同级会生成logs/文件夹 gc-**.log jvm内存监控信息 jvm监控端口1091
### 可远程快捷部署以及sql打印开关 http://ip:port/admin 


## 接口文档
### http://ip:port/swagger-ui.html

##项目用到技术
### 代码生成 运行com.modules.gen.application.Exec
### swagger
### easypoi,kaptcha,FastJson
### spring-mail
### Interceptors,Scheduled,WebListener,Webservice
### 支持 Quartz 调度程序 未写实例
### 支持netty 长连接 未写实例
##demo描述
###系统用户为Worker 客户MemberBaseInfo