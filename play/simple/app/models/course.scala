package models


import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import slick.backend.DatabaseConfig
import slick.driver.MySQLDriver

case class Course(id: Int, name: String)


class CourseTable(tag: Tag) extends Table[Course](tag, "course") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    override  def * = (id, name) <> (Course.tupled, Course.unapply)
}

object Courses {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val courses = TableQuery[CourseTable]

  def listAll: Future[Seq[Course]] = {
    dbConfig.db.run(courses.result)
  }

}