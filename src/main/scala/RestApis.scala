import java.text.SimpleDateFormat
import java.util.Calendar

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import org.slf4j.LoggerFactory

import scala.util.Properties

class RestApis extends SprayJsonSupport {
  val routes = path("health") {
    get {
      complete("{Status:OK}")
    }
  } ~
    path("greet" / Segment) { (name: String) =>
      get {
        complete(s"{message:Hello $name}")
      }
    } ~
    path("date") {
      get {
        val dateFormat = new SimpleDateFormat("d-M-y")
        val date = dateFormat.format(Calendar.getInstance().getTime)
        complete(s"{date:$date}")
      }
    }
}

object RestApis {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("RestApis")
    val restApis = new RestApis
    val port = Properties.envOrElse("PORT", "8080").toInt
    val logger = LoggerFactory.getLogger("AkkaHttpApi")
    logger.info(s"starting server at $port")
    Http().newServerAt("localhost", port).bind(restApis.routes)

  }
}
