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
import services._

case class User(id:Int,name:String)

@Singleton
class HomeController @Inject()(testService: TestService, cc: ControllerComponents) extends AbstractController(cc) {

  private var num : Int = 0;

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect("/main")
  }

  def fetchSth() = Action {
    Ok(Json.toJson(Json.obj(
      "id" -> 1,
      "name" -> "123"
    )))
  }

  def fetchUser() = Action {
    implicit val userWrites = Json.writes[User];
    Ok(Json.toJson(User(1, "bbb")))
  }

  def hello(name: String) = Action {
    Ok("Hello " + name)
  }

  def main = Action {
    Ok(views.html.index())
  }

  def test = Action {
    Ok(views.html.test())
  }

  def testAdd = Action {
    num = num + 1
    testService.addCount()
    Ok("")
  }

  def testMinus = Action {
     num = num - 1
    testService.minusCount()
    Ok("")
  }

  def testShow = Action {
    val l = testService.showCount();
    val a = Json.obj(
          "data" -> l,
          "num" -> num
        )
    Ok(Json.toJson(a))
  }



  val userForm = Form(
      mapping(
        "id" -> number,
        "name" -> text
      )(User.apply)(User.unapply)
    )

  def saveSth = Action { 
    Ok("yes") 
  }


}
