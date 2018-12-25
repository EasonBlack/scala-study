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
class Home2Controller @Inject()(testService: TestService, cc: ControllerComponents) extends AbstractController(cc) {
 
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.home2())
  }
  def indexnext() = Action { implicit request: Request[AnyContent] =>
     request.session.get("flag")  match {
        case None => Ok(views.html.home2next(""))
        case Some(flag) => Ok(views.html.home2next(flag))
     }
  }

  def next() = Action { request => 
    val a =   request.queryString.get("flag").map(_.head)
    println(a)
    a match {
      case None =>  Redirect("/home2")
      case Some(flag) => Ok("1").withSession("flag" -> flag)
    }
  }
 

}
