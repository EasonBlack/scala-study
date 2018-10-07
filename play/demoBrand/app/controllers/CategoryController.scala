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

import services.CategoryService
import models._

import utils.JsonUtils.{categoryFormat }


import scala.concurrent.ExecutionContext

 
@Singleton
class CategoryController @Inject()(categoryService: CategoryService, cc: ControllerComponents)
 (implicit exec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.category())
  }

  def fetchByBrandId(brandid: Int) = Action.async { implicit request: Request[AnyContent] =>
    categoryService.fetchByBrandId(brandid) map {
      categorys => Ok(Json.toJson(categorys))
    }
  }

  def addCategory() = Action.async { implicit request: Request[AnyContent] =>

    val jsonBody  =  request.body.asJson.get
    val items  = jsonBody.as[JsArray]
    val oneCat = items(0).as[Category]
    val cats =  items.value.map {
      item => { 
        item.as[Category]
      }
    }
    println(cats);
   
    categoryService.createCategorys(cats) map {
      r => { 
         Ok(Json.toJson(r))
      }
    }
   
    
   
    
    //val aa:JsArray = items
    

  }

}