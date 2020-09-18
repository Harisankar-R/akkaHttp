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
            s"{message:Hello from $name}")
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
