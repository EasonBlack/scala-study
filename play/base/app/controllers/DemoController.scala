package eason.controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global



@Singleton
class DemoController @Inject()(cc: ControllerComponents) extends BaseController {
  
  def index() = Action {
    Ok("This is home")
  }

  def empty() = Action {
    emptyResult;
  }
  def somthing() = Action {
    dataResult(Json.obj({
      "a"->123
    }))
  }

  def somthing2() = Action {
   val a = "bbb"
   val a1 = a match {
     case "" => "none"
     case _ => "some"
   }
   val b:Option[String] = None
   val b1 = b match {
     case None => "none"
     case Some(d) => d
   }

   b match {
     case None =>  println("none")
     case Some(d) => println("some")
   }
   Ok(a1 + b1)
  }

  def somthing3() = Action {
    Ok(Json.obj(
      "a" -> 1,
      "b" -> 2
    ))
  }

  def somthing31() = Action {
    val a = Map(
      "a"->1, 
      "b"->2,
      "c"->Seq(
        Json.obj(
          "d"-> 11,
          "e"-> 33
        ),
        Json.obj(
          "d"-> 22,
          "e"-> 44
        )
      )
    )
    Ok(a.toString)
  }
  
}
