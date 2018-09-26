package services

import models.{Student, Students}
import scala.concurrent.Future

object StudentService {

  def listAllStudents: Future[Seq[Student]] = {
    Students.listAll
  }
}