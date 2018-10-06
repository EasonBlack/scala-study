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
import utils.JsonUtils.{ruleWrites,ruleReads, ruleInfoWrites }

import scala.concurrent.ExecutionContext


@Singleton
class BrandRuleController @Inject()(brandService: BrandService, ruleService: RuleService, cc: ControllerComponents)
 (implicit exec: ExecutionContext) extends AbstractController(cc) {

 
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.brandrule())
  }

  def fetchRules() = Action.async { implicit request: Request[AnyContent] => 
    val iBrandId = request.queryString.get("brandid").map(_.head)
    ruleService.fetchRulesByBrand(iBrandId.get.toInt) map { 
      rules => Ok(Json.toJson(rules))
    } 
  }

  def deleteRule(id: Int) = Action.async { implicit request: Request[AnyContent] => 
    ruleService.deleteRule(id) map { 
      res => Ok(Json.toJson(res))
    } 
  }

  def updateRule(id: Int) = Action.async { implicit request: Request[AnyContent] => 
    val json = request.body.asJson.get
    val rule = json.as[Rule]
    println(rule)
    ruleService.updateRule(id, rule) map {
      res  => Ok(Json.toJson(res))
    }
  }

  def fetchRulesInfo() =Action.async { implicit request: Request[AnyContent] =>  
    val iBrandId = request.queryString.get("brandid").map(_.head)
    ruleService.fetchRulesInfo(iBrandId.get.toInt) map {
        rules => Ok(Json.toJson(rules))
    }
  }

  def addRule() = Action.async { implicit request: Request[AnyContent] => 
    val json = request.body.asJson.get
    val rule = new Rule(None, (json \ "name").as[String], (json \ "operator").as[String], (json \ "num").as[String], (json \ "brandid").as[Int])
    println(json)
    println( (json \ "name").as[String])
    println(rule)
    ruleService.createRule(rule) map {
      r => {
        Ok(Json.toJson(r))
      }
    }
  }

  def addRule2() = Action.async { implicit request: Request[AnyContent] => 
    val json = request.body.asJson.get
    val rule = json.as[Rule]
    println(rule)
    ruleService.createRule(rule) map {
      r => {
        Ok(Json.toJson(r))
      }
    }
  }

 }