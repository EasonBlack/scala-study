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

import services.BrandService
import services.RuleService
import models._




import scala.concurrent.ExecutionContext

 
@Singleton
class BrandRuleController @Inject()(brandService: BrandService, ruleService: RuleService, cc: ControllerComponents)
 (implicit exec: ExecutionContext) extends AbstractController(cc) {
  
  implicit val ruleWrites = Json.writes[Rule]

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.brandrule())
  }

  def fetchRules() = Action.async { implicit request: Request[AnyContent] => 
    val iBrandId = request.queryString.get("brandid").map(_.head)
    ruleService.fetchRulesByBrand(iBrandId.get.toInt) map { 
      rules => Ok(Json.toJson(rules))
    } 
  }

  def addRule() = Action.async { implicit request: Request[AnyContent] => 

   
    val json = request.body.asJson.get
    
    val rule = new Rule(None, (json \ "name").as[String], (json \ "operator").as[String], (json \ "num").as[String], (json \ "brandid").as[Int])
    println(json)
    //println(rule)
    println( (json \ "name").as[String])
    println(rule)
    // println( Integer.parseInt((json \ "brandid").as[String]))
    // println(rule)
    //Ok
    ruleService.createRule(rule) map {
      r => {
        Ok(Json.toJson(r))
      }
    }

  }

 }