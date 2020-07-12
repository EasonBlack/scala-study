package services

import models._
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play

import slick.jdbc.{GetResult}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ProductService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
 
  val _product2Tables = Tables.product2Tables



      
  def fetchProduct1(): Future[Seq[Product2]] = {
    db.run(_product2Tables.result)
  }


   
}