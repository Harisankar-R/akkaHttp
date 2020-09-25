

name := "akkaHttp"

version := "0.1"

scalaVersion := "2.12.12"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.0"



val httpService = (project in file(".")).settings(
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
    "org.scalatest" %% "scalatest" % "3.2.0" % "test",
    "org.slf4j" % "slf4j-api" % "1.7.5",
    "org.slf4j" % "slf4j-simple" % "1.7.5"
  ),
  mainClass in assembly := Some("RestApis"),
  assemblyJarName in assembly := "akka-http-apis.jar"
)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)

packageName in Docker := "akkahttp"
version in Docker := "latest"
dockerBaseImage := "openjdk"
dockerExposedPorts := Seq(8080)
