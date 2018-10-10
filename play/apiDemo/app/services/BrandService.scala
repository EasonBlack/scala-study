package services

import models._
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play

@Singleton
class BrandService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _brandTables = Tables.brandTables
      
  def fetchAll(): Future[Seq[Brand]] = {
    db.run(_brandTables.filter(_.id=!=1).result)
  }

  def autoAdd1() : Future[Unit] = {
    db.run(DBIO.seq(
      _brandTables += Brand(None, "aaaa"),
      _brandTables += Brand(None, "bbbb")
    ))
  }

  def autoAdd2(brands: Seq[Brand]) : Future[Option[Int]] = {
    db.run(_brandTables ++= brands )
  }
   
}