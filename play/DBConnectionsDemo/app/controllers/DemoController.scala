package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

import services.{Test1DemoService, Test2DemoService}
import entities._

@Singleton
class DemoController @Inject()(cc: ControllerComponents
, test1DemoService: Test1DemoService
, test2DemoService: Test2DemoService
) extends AbstractController(cc) {

  implicit val test1DemoFormat = Json.format[Test1Demo]
  implicit val test2DemoFormat = Json.format[Test2Demo]
  
  def index() = Action {  request =>
    Ok("This is home")
  }

  def empty() = Action {
    Ok("")
  }

  def getTest1Demo = Action.async { request =>
    test1DemoService.fetchTest1Demo().map { result => 
      Ok(Json.toJson(result))
    }
  }
  def getTest2Demo = Action.async { request =>
    test2DemoService.fetchTest2Demo().map { result => 
      Ok(Json.toJson(result))
    }
  }
    
}
