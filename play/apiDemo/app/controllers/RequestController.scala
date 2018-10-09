package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

@Singleton
class RequestController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def fetch1() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(Json.obj("a"-> 1, "b"->2)))
  }

}