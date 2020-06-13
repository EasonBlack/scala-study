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
import utils._
import java.text.SimpleDateFormat
import java.sql.Timestamp
import java.util.{Calendar, Date}


import scala.concurrent.{ExecutionContext, Future}

 
@Singleton
class DataController @Inject()( cc: ControllerComponents)
 (implicit exec: ExecutionContext) extends AbstractController(cc) {

  private val dateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
  private val dateFormat2 = new SimpleDateFormat("yyyy-MM-dd")
  def fetchAll() = Action.async { implicit request: Request[AnyContent] =>  
    {
      println( new SimpleDateFormat("yyyy-MM-dd").format(new Timestamp(dateFormat.parse("20191201040000").getTime)))
      //println( new Timestamp(new Date("2020-01-01").getTime()))
      Future.successful(Ok(Json.toJson(
        Constant.data1
      )))
    }
  }

}
