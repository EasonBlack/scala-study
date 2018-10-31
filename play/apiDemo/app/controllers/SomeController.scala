package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import play.api.Configuration


@Singleton
class SomeController @Inject()( cc: ControllerComponents, configuration: Configuration)
(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def fetch1() = Action { implicit request: Request[AnyContent] =>
     Ok(configuration.get[String]("testApplicationConf"))
  }


}