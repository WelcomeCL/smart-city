# 使用JDK 21作为基础镜像（兼容Java 25）
FROM eclipse-temurin:21-jre

# 设置工作目录
WORKDIR /app

# 复制jar包到容器中
COPY target/admin-service-0.0.1-SNAPSHOT.jar /app/app.jar

# 设置JVM参数
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"

# 暴露端口
EXPOSE 8090

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]