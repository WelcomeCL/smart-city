# 使用预编译的jar文件构建镜像
FROM openjdk:21-jdk
# 使用OpenJDK Java 21稳定版本
# 设置JVM参数
ENV JAVA_OPTS="-Dspring.config.import=optional:nacos:"

# 设置环境变量
ENV SPRING_PROFILES_ACTIVE=prod
ENV NACOS_SERVER_ADDR=nacos-server:8848
ENV NACOS_NAMESPACE=public

# 设置工作目录
WORKDIR /app

# 复制已编译好的jar文件
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8761

# 启动命令，确保JAVA_OPTS被正确使用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8761/actuator/health || exit 1