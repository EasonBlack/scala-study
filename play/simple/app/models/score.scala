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

object ScoreFormat {
  implicit val scoreFormat = Json.format[Score]
}
case class Score(id: Int, studentid: Int, courseid: Int, num: Int)



class ScoreTable(tag: Tag) extends Table[Score](tag, "score") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def studentid = column[Int]("studentid")
    def courseid = column[Int]("courseid")
    def num = column[Int]("num")
    override  def * = (id, studentid, courseid, num) <> (Score.tupled, Score.unapply)
}

object Scores {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val scores = TableQuery[ScoreTable]

  def listAll: Future[Seq[Score]] = {
    dbConfig.db.run(scores.result)
  }

}