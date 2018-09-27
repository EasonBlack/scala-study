package services

import models.{StudentJoin, StudentsJoin}
import scala.concurrent.Future

object StudentJoinService {

  def listAllStudentsJoin: Future[Seq[StudentJoin]] = {
    StudentsJoin.listAll
  }
}