# 使用预编译的jar文件构建镜像
FROM openjdk:25-ea-jdk
# 使用OpenJDK Java 25早期访问版本，它应该支持Java 25编译的class file format (version 69.0)
# 设置JVM参数以支持更高版本的class file format
ENV JAVA_OPTS="--enable-preview -Dspring.config.import=optional:nacos: -Dspring.cloud.nacos.config.import-check.enabled=false"

# 设置环境变量
ENV SPRING_PROFILES_ACTIVE=prod
ENV NACOS_SERVER_ADDR=nacos-server:8848
ENV NACOS_NAMESPACE=public
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/user_db?useSSL=false&serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=password

# 设置工作目录
WORKDIR /app

# 复制已编译好的jar文件
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口
EXPOSE 8081

# 启动应用，确保JAVA_OPTS被正确使用
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8081/actuator/health || exit 1