package BaselineControllerSpec

import controllers.BaselineActions
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{Json, JsValue}
import play.api.mvc.{Result, Controller, Results}
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, Helpers, FakeRequest}

import scala.concurrent.Future

class BaselineControllerSpec extends PlaySpec with Results {
  def makeFakeRequest(buildingType: String, resource: String):FakeRequest[JsValue] = {
    val js = scala.io.Source.fromInputStream(getClass.getResourceAsStream(resource)).mkString
    FakeRequest(Helpers.POST, controllers.routes.BaselineController.makeBaseline().url,
      FakeHeaders(), Json.parse(js))
  }
  class TestController() extends Controller with BaselineActions

  "makeBaseline - adult_education post body" should {
    "should be valid" in {
      val controller = new TestController()
      val fakeRequest = makeFakeRequest("", "baseline/adult_education.json")
      val result: Future[Result] = controller.makeBaseline().apply(fakeRequest)
      val bodyTest = contentAsString(result)
      Console.println(bodyTest)
      bodyTest mustBe "ok" // need to check the return valuse
    }
  }

  "makeBaseline - empty post body" should {
    "should be invalid" in {
      val controller = new TestController()
      val fakeRequest = FakeRequest(Helpers.POST, controllers.routes.BaselineController.makeBaseline().url,
        FakeHeaders(), Json.parse(""))

      val result: Future[Result] = controller.makeBaseline().apply(fakeRequest)
      val bodyTest = contentAsString(result)
      Console.println(bodyTest)
      bodyTest mustBe "ok" // need to check the return valuse
    }
  }
}
