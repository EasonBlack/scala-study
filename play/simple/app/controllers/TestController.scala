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


@Singleton
class TestController @Inject()( cc: ControllerComponents) extends  Test1Controller(cc)  {
 
 

}
