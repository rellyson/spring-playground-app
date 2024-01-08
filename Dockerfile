FROM maven:3-eclipse-temurin-17 as builder
WORKDIR /tmp/build
ENV JAR_FILE=target/application.jar

COPY . .
RUN mvn clean install package
RUN java -Djarmode=layertools -jar $JAR_FILE extract


# ---------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /usr/src/app
ENV SPRING_PROFILES_ACTIVE=container

# create rootless group/user
RUN addgroup --system java && adduser -S -H -s /usr/sbin/nologin -G java java

COPY --from=builder /tmp/build/dependencies/ .
COPY --from=builder /tmp/build/spring-boot-loader/ .
COPY --from=builder /tmp/build/snapshot-dependencies/ .
COPY --from=builder /tmp/build/application/ .

RUN chown -R java:java /usr/src/app

USER java
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
