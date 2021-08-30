FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /application
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn clean -Dmaven.test.skip package
RUN cp ./target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM openjdk:11-jre-slim
WORKDIR /application
COPY --from=build /application/dependencies/ ./
COPY --from=build /application/snapshot-dependencies/ ./
COPY --from=build /application/spring-boot-loader/ ./
COPY --from=build /application/application/ ./
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
