package services

import models.{Course, Courses}
import scala.concurrent.Future

object CourseService {

  def listAllCourses: Future[Seq[Course]] = {
    Courses.listAll
  }
}