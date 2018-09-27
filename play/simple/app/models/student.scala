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

import models.{Course, CourseTable}

case class Student(id: Int, name: String, age: String)


class StudentsTable(tag: Tag) extends Table[Student](tag, "student") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def age = column[String]("age")

    override  def * = (id, name, age) <> (Student.tupled, Student.unapply)
}

object Students {

  val dbConfig: DatabaseConfig[MySQLDriver] = DatabaseConfig.forConfig("slick.dbs.default")

  //val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val students = TableQuery[StudentsTable]

  def listAll: Future[Seq[Student]] = {
    dbConfig.db.run(students.result)
    //dbConfig.db.run(students.result)
  }

}