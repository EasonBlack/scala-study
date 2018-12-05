package services

import models._
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BrandService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _brandTables = Tables.brandTables
  val _productTables = Tables.productTables

  println(Tables.test);
      
  def fetchAll(): Future[Seq[Brand]] = {
    db.run(_brandTables.filter(_.id=!=1).result)
  }

  def fetchAllProduct: Future[Seq[Product]] = {
    // db.run(_productTables.result)
    // 需要 (implicit ec: ExecutionContext)
    db.run(_productTables.result).map {
      result => result
    }
  }

  def fetchProductBranch: Future[Seq[(Product, Brand)]] = {
    db.run((_productTables join _brandTables on (_.bid === _.id)).result).map { result =>
      println(result)
      result
    }
  }

  def fetchProductBranch2: Future[Seq[(Product, String)]] = {
    db.run((_productTables join _brandTables on (_.bid === _.id)).result).map { 
      result => result.map {
        item => (item._1, item._2.name)
      }
    }
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