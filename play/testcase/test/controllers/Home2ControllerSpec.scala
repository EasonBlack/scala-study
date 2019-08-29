package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class Home2ControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "render the index page from a new instance of controller" in {
      val controller = new Home2Controller(stubControllerComponents())
      val home = controller.index().apply(FakeRequest(GET, "/home2"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play2")
    }

    "render the index page from the application" in {
      val controller = inject[Home2Controller]
      val home = controller.index().apply(FakeRequest(GET, "/home2"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play2")
    }

  
  }
}
