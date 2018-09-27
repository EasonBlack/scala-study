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
import play.api.libs.json._

import models._

case class StudentJoin(id: Int, name: String,  scoreid: Int,  num: Int)

object StudentJoin {
  implicit val studentJoinFormat = Json.format[StudentJoin]
}

object StudentsJoin {

  val dbConfig: DatabaseConfig[MySQLDriver] = DatabaseConfig.forConfig("slick.dbs.default")
  val students = TableQuery[StudentsTable]
  val scores = TableQuery[ScoreTable]

  val studentsJoin = for {
    (stu, score) <- students join scores on (_.id === _.studentid)
  } yield (stu, score)

  val studentsJoin2 = for {
    (stu, score) <- students join scores on (_.id === _.studentid)
  } yield (stu.id, stu.name, score.id, score.num)

  def listAll: Future[List[StudentJoin]] = {

    dbConfig.db.run(studentsJoin.result) map {
      dataTuples  => {
        val groupedByScore = dataTuples.groupBy(_._2)
        groupedByScore.map {
          case (scoreRow, tuples) => {
            val student = tuples.map(_._1).map { p => Student(p.id, p.name, p.age)}
            println(student(0))
            StudentJoin(
              id = student(0).id,
              name= student(0).name,
              scoreid = scoreRow.id,
              num = scoreRow.num
            )
          }
        }.toList     
      }
    }
   
  }

  def listAll2: Future[List[StudentJoin]] = {
    dbConfig.db.run(studentsJoin2.result) map {
      dataTuples => { 
          dataTuples map { d => 
            println(d)
            StudentJoin(
                d._1,
                d._2,
                d._3,
                d._4
              )
            }
      }.toList
    }
  }

}