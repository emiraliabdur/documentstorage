FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
ENTRYPOINT ["sh", "-c", "java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9094 -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]