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
### 可远程快捷部署以及sql打印开关 访问http://ip:port/admin 


## 接口文档
### http://ip:port/swagger-ui.html

##项目用到技术

### swagger
### easypoi(pdf操作),kaptcha（验证码生成）
### spring-mail(邮件发送)
### Interceptors,Scheduled,WebListener,Webservice
### 支持 Quartz 调度程序 未写实例
### 支持netty 长连接 未写实例
### dubbo注释放开,配置环境就能用 demo在TestControler(/dubbo)
##demo描述
###系统用户为Worker 客户MemberBaseInfo

## 代码生成 
### 运行com.modules.gen.application.Exec

## 项目描述
#### 项目适用于前后端分离类型项目后台，为解决后台快速开发编写的项目。
#### 理解项目需要先看各个文件夹以及com.*一级和二级包的作用
#### 整体项目流程 用户登陆存储信息在redis 通过拦截器判断用户是否登陆，接口访问权限可在此处实现，目前项目未添加
#### 对外接口文档通过swagger控制
#### 目前提供的基础模块 worker员工 member客户 mail邮件 sms短信模块需根据第三方接口做调整 sysdict系统字典 callarea归属地 dept员工部门  role员工角色 phonetag手机标签 gen代码生成 

