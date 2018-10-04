package services

import models._
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class RuleService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]  {
 
  val _ruleTables = Tables.ruleTables
      
  def createRule(rule: Rule): Future[Int]  = {
    db.run(_ruleTables += rule)
  }

  def fetchRulesByBrand(id: Int) : Future[Seq[Rule]] = {
    //db.run(_ruleTables.result)
    db.run(_ruleTables.filter(_.brandid===id).result)
  }
   
}