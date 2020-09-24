import java.text.SimpleDateFormat
import java.util.Calendar

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import org.slf4j.LoggerFactory

class RestApis extends SprayJsonSupport {
  val logger = LoggerFactory.getLogger("AkkaHttpApi")
  val routes = path("health") {
    get {
      logger.info("hitting /health")
      complete("{Status:OK}")
    }
  } ~
    path("greet" / Segment) { (name: String) =>
      get {
        logger.info(s"hitting /greet/$name")
        complete(s"{message:Hello $name}")
      }
    } ~
    path("date") {
      get {
        logger.info("hitting /date")
        val dateFormat = new SimpleDateFormat("d-M-y")
        val date = dateFormat.format(Calendar.getInstance().getTime)
        complete(s"{date:$date}")
      }
    }
}

object RestApis {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("RestApis")
    val logger = LoggerFactory.getLogger("AkkaHttpApi")
    val restApis = new RestApis
    val port = 8080
    logger.info(s"starting server at $port")
    Http().newServerAt("0.0.0.0", port).bind(restApis.routes)
  }
}
