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

  def send12() = Action { implicit request: Request[AnyContent] =>
    println(request.queryString)
    val ids = request.queryString("ids")
    println(ids + " from query");
    val idsSeq = Seq(ids)
    println(idsSeq)
    idsSeq map { id =>
      println(id)
    }

    println("xxxxxxxxxxxx")
    var a = request.queryString.toList
    println(request.queryString.toList)
    println(a(0))
    // println(a(0).ids)
    println("xxxxxxxxxxxx")
    val ids2 = request.queryString("ids")
    val name = request.queryString.get("name")
    val code = request.getQueryString("code")
    val name2 = name match {
      case None => None
      case Some(b) => {
        b.head match {
          case "0" => None
          case _ => b
        }
      }
     
    }
    val code2 = code match {
      case None => None

      case Some(c) => Seq(c)
    }
    
    println(ids2)
    println(name)
    println("name2 is "  + name2)
    println(code2)
    
    Ok(ids2 + " from query")
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