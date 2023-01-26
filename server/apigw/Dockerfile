FROM eclipse-temurin:17-jdk@sha256:5e71493375b09083fb49cbd17dabf3194e09e2b575c6f91a80a643cf511666b0
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]