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

import play.api.Play.current
import scala.collection.mutable.MutableList
import play.api.db._
import models.Student


@Singleton
class StudentController @Inject()(db: Database,  cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
      
    val list = MutableList[Student]()
    val conn = db.getConnection()
    
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT id, name, age from student ")
      
      while (rs.next()) {
        println(rs.getString(1))
        list.+=(Student(rs.getString(1), rs.getString(2), rs.getString(3)))
      }
    } finally {
      conn.close()
    }
   
    Ok(views.html.list(list))
  }

  def fetch() =  Action { implicit request: Request[AnyContent] => 
    val list = MutableList[Student]()
    val conn = db.getConnection()
    implicit val studentWrites = Json.writes[Student];
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT id, name, age from student ")
      
      while (rs.next()) {
        println(rs.getString(1))
        list.+=(Student(rs.getString(1), rs.getString(2), rs.getString(3)))
      }
    } finally {
      conn.close()
    }
   
    Ok(Json.toJson((list)))
    
  }

  

}
