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
class OrderController @Inject()(orderService: OrderService, cc: ControllerComponents)
(implicit exec: ExecutionContext) extends AbstractController(cc) { 
  
  implicit val orderFormat = Json.format[Order]
  implicit val orderRelateFromat = Json.format[OrderRelate1]
  implicit val orderRelate2Fromat = Json.format[OrderRelate2]
  implicit val orderItemFormat = Json.format[OrderItem]
  implicit val orderItemAccountFormat = Json.format[OrderItemAccount]

  implicit private[this] val getResults1_1 = Json.format[(Order, Seq[OrderItem])] 
  implicit private[this] val getResults1_2 = Json.format[(OrderItem, OrderItemAccount)] 
  implicit private[this] val getResults1_3 = Json.format[(Order, Seq[(OrderItem, OrderItemAccount)])] 

  // implicit private[this] val getResults1_4 = Json.format[(Order, Seq[(OrderItem, Seq[OrderItemAccount])])] 



  implicit private[this] val getResults1_4 = new Writes[(Order, Seq[(OrderItem, Seq[OrderItemAccount])])] {
    override def writes(o: (Order, Seq[(OrderItem, Seq[OrderItemAccount])])): JsValue = Json.obj(
       "order" -> Json.toJson(o._1),
       "orderItems" -> JsArray(o._2.map {
          case (orderItem, orderItemAccounts) =>
            Json.obj(
              "id" -> orderItem.id,
              "name" -> orderItem.name,
              "accounts" -> JsArray(orderItemAccounts map { account =>
                Json.obj(
                  "name" -> account.name
                )
              })
            )
          
       })
    )
  }

   implicit private[this] val getResults1_5 = new Writes[( (Order, OrderRelate1), Seq[(OrderItem, Seq[OrderItemAccount])])] {
    override def writes(o: ( (Order, OrderRelate1), Seq[( OrderItem, Seq[OrderItemAccount])])): JsValue = Json.obj(
       "order" -> Json.toJson(o._1._1),
       "orederRelate" -> Json.toJson(o._1._2),
       "orderItems" -> JsArray(o._2.map {
          case (orderItem, orderItemAccounts) =>
            Json.obj(
              "id" -> orderItem.id,
              "name" -> orderItem.name,
              "accounts" -> JsArray(orderItemAccounts map { account =>
                Json.obj(
                  "name" -> account.name
                )
              })
            )
          
       })
    )
  }

  implicit private[this] val getResults1_6 = new Writes[( Order, OrderRelate1, OrderRelate2, Seq[(OrderItem, Seq[OrderItemAccount])])] {
    override def writes(o: ( Order, OrderRelate1,OrderRelate2, Seq[( OrderItem, Seq[OrderItemAccount])])): JsValue = Json.obj(
       "order" -> Json.toJson(o._1),
       "orederRelate1" -> Json.toJson(o._2),
       "orederRelate2" -> Json.toJson(o._3),
       "orderItems" -> JsArray(o._4.map {
          case (orderItem, orderItemAccounts) =>
            Json.obj(
              "id" -> orderItem.id,
              "name" -> orderItem.name,
              "accounts" -> JsArray(orderItemAccounts map { account =>
                Json.obj(
                  "name" -> account.name
                )
              })
            )
          
       })
    )
  }



  def fetch1() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def fetch1_1() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder1_1 map { 
      items => Ok(Json.toJson(items))
    } 
  }
  def fetch1_2() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder1_2 map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def fetch1_3() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder map { 
      items => Ok(JsArray(items.map { case (item) =>
        Json.obj(
          "id" -> item._1.id
        )
      }))
    } 
  }

  def fetch2() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder2 map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def fetch3() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder3 map { 
      items => Ok(Json.toJson(items))
    } 
  }

  def fetch4() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder4 map { 
      items => Ok(Json.toJson(items))
    } 
  }
  def fetch5() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder5 map { 
      items => Ok(Json.toJson(items))
    } 
  }
  def fetch6() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder6 map { 
      items => Ok(Json.toJson(items))
    } 
  }
  def fetch7() = Action.async { implicit request: Request[AnyContent] =>
    orderService.fetchOrder7 map { 
      items => Ok(Json.toJson(items))
    } 
  }
  def fetch8() = Action.async { implicit request: Request[AnyContent] =>
    val name  = request.queryString.get("name").map(_.head)
    val id  = request.queryString.get("id").map(_.head)
    orderService.fetchOrder8(name, id) map { 
      items => Ok(Json.toJson(items))
    } 
  }
  

}