FROM maven:3.6.0-jdk-11-slim AS build
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn clean -Dmaven.test.skip package
RUN cp ./target/*.jar application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM openjdk:11-jre-slim
WORKDIR /usr/src/app
COPY --from=build /usr/src/app/dependencies/ ./
COPY --from=build /usr/src/app/snapshot-dependencies/ ./
COPY --from=build /usr/src/app/spring-boot-loader/ ./
COPY --from=build /usr/src/app/application/ ./
COPY ./wait-for-it.sh ./
RUN chmod +x ./wait-for-it.sh && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
CMD ["./wait-for-it.sh", "localhost:3306", "--timeout=120", "--", "java", "org.springframework.boot.loader.JarLauncher"]
