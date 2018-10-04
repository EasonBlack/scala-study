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
class BrandService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _brandTables = Tables.brandTables
  val _ruleTables = Tables.ruleTables
      
  def fetchAll(): Future[Seq[Brand]] = {
    db.run(_brandTables.result)
  }

  def fetchRuleById: Unit = {
    
  }
   
}