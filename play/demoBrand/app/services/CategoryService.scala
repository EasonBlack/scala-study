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
class CategoryService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _categoryTables = Tables.categoryTables
    
  def fetchByBrandId(id: Int): Future[Seq[Category]] = {
    db.run(_categoryTables.filter(_.brandid===id).result)
  }

  def createCategorys(cats: Seq[Category]): Future[Option[Int]]= {
    db.run(_categoryTables ++= cats)
  }

  def createCategory(cat: Category): Future[Int]= {
    db.run(_categoryTables += cat)
  }
  
}