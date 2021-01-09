package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}
import models._
import services._

import scala.collection.mutable.ListBuffer
import scala.util.Success
import scala.util.Failure


@Singleton
class Brand2Controller @Inject()(brand2Service: Brand2Service, cc: ControllerComponents)
(implicit exec: ExecutionContext) extends AbstractController(cc) { 
  
  implicit val productFormat = Json.format[Product]

  implicit private[this] val getResults1_1 = new Writes[(Product, Option[Int])] {
    override def writes(o: (Product, Option[Int])): JsValue = {
      val productJson = Json.toJson(o._1).asInstanceOf[JsObject]
      o._2 match {
        case None => productJson
        case Some(i) => productJson ++ Json.obj("value" -> i)
      }
    }
  }


  implicit val brandFormat = Json.format[Brand]
 
  implicit val repositoryFormat = Json.format[Repository]

  @deprecated("test deprecated.", since = "x.x.0")
  def fetch1() = Action.async { implicit request: Request[AnyContent] =>
    brand2Service.fetchProductBranch1 map { 
      items => Ok(Json.toJson(items))
    } 
  }
   def fetch1_1() = Action.async { implicit request: Request[AnyContent] =>
    brand2Service.fetchProductBranch1_1 map { 
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

  def fetch4() = Action.async { implicit request: Request[AnyContent] =>
    val names = Seq("A1", "A2")
    brand2Service.fetchProductBranch4(names) map { 
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

  def multipepost = Action.async { request =>
     val names = Seq("aaa", "bbb")
     brand2Service.multipepost(names) map { 
       _ => Ok("successful")
     }
  }

  // def multifetch = Action.async { requst => 
  //   var a = List(1,2,3,4)
  //   Future.sequence(a.map {
  //     t => brand2Service.getBrandHeadName(t)
  //   }.toSeq).map { res =>
  //     println("xxxxxxxxxxxxxxx")
  //     Ok(Json.toJson(res))
  //     // case Success(res) => Ok("Success")
  //     // case Failure(ex)  => Ok("Error")
  //   }

     
  // }

}