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
import scala.concurrent.ExecutionContext.Implicits.global

import models.{Student, Course, Score}
import services.StudentService

import services.CourseService
import services.ScoreService

import models.StudentsJoin



//https://github.com/pedrorijo91/play-slick3-steps/blob/step3/app/controllers/ApplicationController.scala

@Singleton
class StudentSlickController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action.async { implicit request: Request[AnyContent] =>
    StudentService.listAllStudents map { students =>
      println(students)
      Ok(views.html.list(students))
    }
  }

  def courseList() = Action.async { implicit request: Request[AnyContent] =>
    CourseService.listAllCourses map { courses =>
      Ok(views.html.course(courses))
    }
  }

  def scoreList() = Action.async { implicit request: Request[AnyContent] =>
    ScoreService.listAllScores map { scores =>
      Ok(views.html.score(scores))
    }
  }

  def studentJoinList() =  Action.async { implicit request: Request[AnyContent] =>
    StudentsJoin.listAll map {
       s => Ok(Json.toJson(s))
    }
  }

  def studentJoinList2() =  Action.async { implicit request: Request[AnyContent] =>
    StudentsJoin.listAll2 map {
       s => Ok(Json.toJson(s))
    }
  }

 
}
