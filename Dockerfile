FROM openjdk:8
WORKDIR /app/apis
#fetch artifact and copy to /app/apis
COPY akka-http-apis.jar /app/apis
EXPOSE 8080
CMD ["java","-cp","akka-http-apis.jar","RestApis"]