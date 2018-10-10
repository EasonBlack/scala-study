package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.{Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global


case class User(id: Int, name: String)

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val userWrites = Json.writes[User];

  def index() = Action {
    Ok(views.html.home())
  }

  def fetch1() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(Json.obj("a"-> 1, "b"->2)))
  }

  def fetch2() = Action { implicit request: Request[AnyContent] =>    
    Ok(Json.toJson(User(1, "aaaa")))
  }

  def fetch3() = Action { implicit request: Request[AnyContent] =>    
    Ok(Json.toJson((User(1, "aaaa"),User(2, "bbb"))))
  }

  def fetch4() = Action { implicit request: Request[AnyContent] =>   
    implicit val v = User(1, "bbbb")
    def myJson(implicit cc: User): JsValue = {
      Json.toJson(cc)  
    }
    Ok(myJson)
  }

  def fetch5() = Action { implicit request: Request[AnyContent] =>   
    val res1 = Json.arr(  
        Json.obj("a"-> 2, "b"->2),
        Json.obj("a"-> 2, "b"->3)
    )
    val res2 = Json.obj("c"-> 33, "d" ->44)
    println(res1 \\ "a")
    println(res2 \ "c")
    Ok(Json.toJson(res1))
  }

  def fetch6() = Action.async { implicit request: Request[AnyContent] =>   
    val f = Future {
      //Thread.sleep(500)
      Json.obj("a"-> 6, "b"->5)
    }
    f map {
      data => Ok(data)
    }
  }

   def fetch7() = Action.async { implicit request: Request[AnyContent] =>   
    Future(Ok("Success"))
  }
}
