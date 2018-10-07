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
class ProductService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _productables = Tables.productTables
  val _brandTables = Tables.brandTables
  val _categoryTables = Tables.categoryTables;
  val _productInfotables = for {
    ((product, category), brand) <- _productables joinLeft _categoryTables on (_.categoryid === _.id) joinLeft _brandTables on (_._2.map(_.brandid) === _.id )
  } yield (product, category , brand)
    
  def fetchByCategoryId(categoryid: Int): Future[Seq[Product]] = {
    db.run(_productables.filter(_.categoryid === categoryid).result)
  }

  def fetchInfoByCategoryId(categoryid: Int): Future[List[ProductInfo]] = {
    db.run(_productInfotables.filter(_._1.categoryid===categoryid).result) map {
      dataTuples  => {
        dataTuples map { d=>
          ProductInfo(
            d._1.id,
            d._1.name,
            d._1.categoryid,
            d._2.get,
            d._3.get
          )
        }
      }.toList
    } 
  }

  

  def createProducts(pros: Seq[Product]): Future[Option[Int]]= {
    db.run(_productables ++= pros)
  }
  
}