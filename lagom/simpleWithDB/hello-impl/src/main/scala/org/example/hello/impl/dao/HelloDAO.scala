package org.example.hello.impl.dao

import java.sql.{SQLIntegrityConstraintViolationException, Timestamp}
import java.text.SimpleDateFormat
import java.util.{Calendar, Date, UUID}

import com.lightbend.lagom.scaladsl.persistence.EventStreamElement
import play.api.Logger
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}


class HelloDAO(val db: Database)(implicit val exec: ExecutionContext) extends SlickMySqlDaoComponent {

   def getHelloList(id: Long): Future[Option[HelloDTO]] = {
    db.run(sql"""SELECT id, name FROM hello WHERE id = $id""".as[(Long, String)].headOption).flatMap {
      case None => Future.successful(None)
      case Some((id, name)) => HelloDTO(id, name)
    }
  }
}