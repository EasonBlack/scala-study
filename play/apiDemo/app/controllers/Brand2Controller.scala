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
class Brand2Controller @Inject()(brand2Service: Brand2Service, cc: ControllerComponents)
(implicit exec: ExecutionContext) extends AbstractController(cc) { 

  implicit val brandFormat = Json.format[Brand]
  implicit val productFormat = Json.format[Product]
  implicit val repositoryFormat = Json.format[Repository]

   def fetch1() = Action.async { implicit request: Request[AnyContent] =>
    brand2Service.fetchProductBranch1 map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def fetch2() = Action.async { implicit request: Request[AnyContent] =>
    brand2Service.fetchProductBranch2 map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def fetch3() = Action.async { implicit request: Request[AnyContent] =>
    brand2Service.fetchProductBranch3 map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def put1 = Action.async { request =>
    val id =  request.queryString.get("id").map(_.head) match {
      case None => 0
      case Some(d) => d.toInt 
    }

    val name =  request.queryString.get("name").map(_.head).getOrElse("")
    brand2Service.putProduct(id, name).map { request=>
      Ok("successful")
    } 
  }
}