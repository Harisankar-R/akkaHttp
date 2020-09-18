import java.text.SimpleDateFormat
import java.util.Calendar

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RestApisTest extends AnyFlatSpec with Matchers with ScalatestRouteTest {

  val restApis = new RestApis

  "RestApi" should "return a OK status on hitting /health" in {
    Get("/health") ~> restApis.routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] should be("{Status:OK}")
    }
  }
  it should "return a message on hitting /greet/<name>" in {
    Get("/greet/Hari") ~> restApis.routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] should be("{message:Hello Hari}")
    }
  }

  it should "return the current date on hitting /date" in {
    val dateFormat = new SimpleDateFormat("d-M-y")
    val date = dateFormat.format(Calendar.getInstance().getTime())
    Get("/date") ~> restApis.routes ~> check {
      status should be(StatusCodes.OK)
      responseAs[String] should be(s"{date:$date}")
    }
  }


}
