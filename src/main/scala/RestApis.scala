import java.text.SimpleDateFormat
import java.util.Calendar

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._

class RestApis {
  val routes = path("health") {
    get {
      complete(
        HttpResponse(StatusCodes.OK, entity = HttpEntity(
          ContentTypes.`application/json`,
          "{Status:OK}"
        )))
    }
  } ~
    path("greet" / Segment) { (name: String) =>
      get {
        complete(HttpResponse(
          StatusCodes.OK,
          entity = HttpEntity(
            ContentTypes.`application/json`,
            s"{message:Hello $name}")
        ))
      }
    } ~
    path("date") {
      get {
        val dateFormat = new SimpleDateFormat("d-M-y")
        val date = dateFormat.format(Calendar.getInstance().getTime())
        complete(HttpResponse(
          StatusCodes.OK,
          entity = HttpEntity(
            ContentTypes.`application/json`,
            s"{date:$date}")
        ))
      }
    }
}

object RestApis {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("RestApis")
    val restApis = new RestApis
    Http().newServerAt("localhost", 8080).bind(restApis.routes)

  }
}
