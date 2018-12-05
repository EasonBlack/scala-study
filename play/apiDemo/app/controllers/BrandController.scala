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
  implicit val productFormat = Json.format[Product]

  def fetch1() = Action.async { implicit request: Request[AnyContent] =>
    brandService.fetchAll map { 
      brands => Ok(Json.toJson(brands))
    } 
  }

  def fetchProduct() = Action.async { implicit request: Request[AnyContent] =>
    brandService.fetchAllProduct map { 
      products => Ok(Json.toJson(products))
    } 
  }

  def fetchProductBrand() = Action.async { implicit request: Request[AnyContent] =>
    brandService.fetchProductBranch map { 
      o => Ok(Json.toJson(o))
    } 
  }

  def fetchProductBrand2() = Action.async { implicit request: Request[AnyContent] =>
    brandService.fetchProductBranch2 map { result =>
      val a =  for (item <- result) yield {
        Json.obj(
          "product" -> item._1,
          "brand" -> item._2
        )
      }
      Ok(Json.toJson(a))
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