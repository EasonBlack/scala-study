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
class BrandController @Inject()(brandService: BrandService, cc: ControllerComponents)
(implicit exec: ExecutionContext) extends AbstractController(cc) {

  implicit val brandFormat = Json.format[Brand]
  implicit val productFormat = Json.format[Product]
  implicit val repositoryFormat = Json.format[Repository]

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
  def fetchProduct2() = Action.async { implicit request: Request[AnyContent] =>
    brandService.fetchAllProduct2 map { 
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

  def fetchProductBrand3() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchProductBranch3 map { result =>
      val a =  for (item <- result) yield {
        Json.obj(
          "brand" -> item._1,
          "repository" -> item._2.name,
          "product" -> item._3
        )
      }
      Ok(Json.toJson(a))
    } 
  }
  def fetchProductBrand4() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchProductBranch4 map { result =>
      val a =  for (item <- result) yield {
        Json.obj(
          "brand" -> item._1,
          "repository" -> item._2.name,
          "product" -> item._3
        )
      }
      Ok(Json.toJson(a))
    } 
  }
  def fetchProductBrand5() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchProductBranch5 map { result =>
      val a =  for (item <- result) yield {
        Json.obj(
          "brand" -> item._1,
          "repository" -> item._2,
          "product" -> item._3
        )
      }
      Ok(Json.toJson(a))
    } 
  }
  def fetchProductBrand6() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchProductBranch6 map { result =>
      val a =  for (item <- result) yield {
        Json.obj(
          "brand" -> item._1,
          "repository" -> item._2,
          "productId" -> item._3,
          "productBid" -> item._4,
          "productName" -> item._5
        )
      }
      Ok(Json.toJson(a))
    } 
  }
  def fetchProductBrand7() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchProductBranch7 map { result =>
      val a =  for (item <- result) yield {
        Json.obj(
          "brand" -> item._1,
          "products"-> item._2
        )
      }
      Ok(Json.toJson(a))
    } 
  }

  def fetchProductBrand8() = Action.async { implicit request: Request[AnyContent] => 
    for {
      brands <- brandService.fetchAll()
      products <- brandService.fetchAllProduct
    } yield {
      Ok(Json.toJson(JsArray(brands map { 
          case(b: Brand) => {
            Json.obj(  
              "id" -> b.id,
              "name" -> b.name,
              "products" -> products.filter(_.bid == b.id.get)
            )
          }
       
      })))
    }
   
  }

  def fetchProductBrand9() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchProductBranch9 map {  result =>
        val a =  for (item <- result) yield {
        Json.obj(
          "brand" -> item._1,
          "count" -> item._2
        )
      }
      Ok(Json.toJson(a))
    }
  }

  def fetchSumRepository() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchSumRepository map {  result =>
        val a =  for (item <- result) yield {
        Json.obj(
          "repository" -> item._1,
          "count" -> item._2
        )
      }
      Ok(Json.toJson(a))
    }
  }

  def fetchSumRepository2() = Action.async { implicit request: Request[AnyContent] => 
    println("xxxxxxxxxxxxx")
    brandService.fetchSumRepository2 map {  result =>
     println("xxxxxxxxxxxxx")
     println(result)
      Ok(Json.toJson(result))
    }
  }

  def fetchSumRepository3() = Action.async { implicit request: Request[AnyContent] => 
    for {
      l <-  brandService.fetchSumRepository3    
    } yield {
      val a = Json.obj(
        "sum" -> l.map(_.num).sum,
        "count" -> l.length
      )
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