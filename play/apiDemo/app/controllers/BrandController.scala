package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import models._
import services._


@Singleton
class BrandController @Inject()(brandService: BrandService, cc: ControllerComponents)
(implicit exec: ExecutionContext) extends AbstractController(cc) {

  implicit val brandFormat = Json.format[Brand]

  def fetch1() = Action.async { implicit request: Request[AnyContent] =>
    brandService.fetchAll map { 
      brands => Ok(Json.toJson(brands))
    } 
  }

  def post1() = Action.async { implicit request: Request[AnyContent] =>
    brandService.autoAdd1() map { 
      res => Ok(Json.obj(
        "status" -> 1,
        "code" -> 1, 
        "message" -> "aaaa"
      ))
    } 
  }

  def post2() = Action.async {  implicit request: Request[AnyContent] =>
    val json = request.body.asJson.get
    val items  = json.as[JsArray]
    val brands =  items.value.map {
      item => { 
        item.as[Brand]
      }
    }

    brandService.autoAdd2(brands) map { 
      res => Ok(Json.toJson(res))
    } 
  }

}