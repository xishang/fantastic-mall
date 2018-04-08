# Eureka Server

## 集群部署

### 1.启动集群

    java -jar eureka-server.jar --spring.profiles.active=server1
    java -jar eureka-server.jar --spring.profiles.active=server2
    
### 2.服务提供方/消费方在`service-url.defaultZone`中注册多个eureka server

    defaultZone: http://localhost1:8001/eureka,http://localhost2:8002/eureka