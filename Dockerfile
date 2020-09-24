FROM openjdk:8
WORKDIR /app/apis
COPY ./target/scala-2.12/akka-http-apis.jar /app/apis
EXPOSE 8080
CMD ["java","-cp","akka-http-apis.jar","RestApis"]