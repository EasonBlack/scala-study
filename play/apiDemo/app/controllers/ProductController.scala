package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import models._
import services._

import scala.collection.mutable.ListBuffer


@Singleton
class ProductController @Inject()(productService: ProductService, cc: ControllerComponents)
(implicit exec: ExecutionContext) extends AbstractController(cc) { 
  
  implicit val productFormat = Json.format[Product]

 
  def fetch1() = Action.async { implicit request: Request[AnyContent] =>
    productService.fetchProduct1 map { 
      items => Ok(Json.toJson(items))
    } 
  }
 

}