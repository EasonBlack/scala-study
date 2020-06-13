package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.http.HttpEntity
import akka.util.ByteString
import play.api.libs.json._
import play.api.mvc.AnyContent
import services._


@Singleton
class Test1Controller @Inject()( cc: ControllerComponents) extends Test2Controller(cc) {
    var testService  = new TestService()
    def t1() = Action { implicit request: Request[AnyContent] =>
        Ok("aaaaa")
    }

    def t11() =Action { implicit request: Request[AnyContent] =>
        Ok(testService.showText())
    }

}
