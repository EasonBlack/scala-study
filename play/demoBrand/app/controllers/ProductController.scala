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

import services.ProductService
import models._

import utils.JsonUtils.{productFormat, productInfoWrites }


import scala.concurrent.ExecutionContext

 
@Singleton
class ProductController @Inject()(productService: ProductService, cc: ControllerComponents)
 (implicit exec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.product())
  }

  def fetchByCategoryId(categoryid: Int) = Action.async { implicit request: Request[AnyContent] =>
    productService.fetchByCategoryId(categoryid) map {
      products => Ok(Json.toJson(products))
    }
  }

  def fetchInfoByCategoryId(categoryid: Int) = Action.async { implicit request: Request[AnyContent] =>
    productService.fetchInfoByCategoryId(categoryid) map {
      products => Ok(Json.toJson(products))
    }
  }

  def addProducts() = Action.async { implicit request: Request[AnyContent] =>

    val jsonBody  =  request.body.asJson.get
    val items  = jsonBody.as[JsArray]
    val pros =  items.value.map {
      item => { 
        item.as[Product]
      }
    }
   
    productService.createProducts(pros) map {
      r => { 
         Ok(Json.toJson(r))
      }
    }
  
  }

}