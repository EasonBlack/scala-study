package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._


case class Rule(id:Int, name:String)

@Singleton
class RequestController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  //implicit val ruleWrites = Json.writes[Rule]
  //implicit val ruleReads = Json.reads[Rule]
  implicit val ruleFormat = Json.format[Rule]

  def send1() = Action { implicit request: Request[AnyContent] =>
    println(request.queryString.get("id"))
    val id = request.queryString.get("id").map(_.head)
    println(id + " from query");
    Ok("Success")
  }

  def send11() = Action { implicit request: Request[AnyContent] =>
    println(request.queryString)
    val id = request.queryString.get("id").map(_.head)
    val name = request.queryString.get("name").map(_.head)
    println(id + " "+ name + " from query");
    Ok("Success")
  }

  def send2(id: Int) = Action { implicit request: Request[AnyContent] =>
    println(id + " from param");
    Ok("Success")
  }

  def send3() = Action { implicit request: Request[AnyContent] =>
    val json = request.body.asJson.get
    val rule = json.as[Rule]
    println(rule)
    Ok(Json.toJson(rule))
  }

  def send4() = Action { implicit request: Request[AnyContent] =>
    val json = request.body.asJson.get
    val items  = json.as[JsArray]
    val rules =  items.value.map {
      item => { 
        item.as[Rule]
      }
    }

    println(rules)
    Ok(Json.toJson(rules))
  }
}