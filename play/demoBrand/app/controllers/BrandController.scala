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
import models._

import utils.JsonUtils.{brandWrites }


import scala.concurrent.ExecutionContext

 
@Singleton
class BrandController @Inject()(brandService: BrandService, cc: ControllerComponents)
 (implicit exec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.brand())
  }

  def fetchAll() = Action.async { implicit request: Request[AnyContent] => 
    brandService.fetchAll map { 
      brands => Ok(Json.toJson(brands))
    } 
  }

}
