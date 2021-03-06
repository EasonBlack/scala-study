package services

import models._
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play

import slick.jdbc.GetResult

import scala.concurrent.{ExecutionContext, Future}

import scala.collection.JavaConverters
import scala.collection.mutable.ListBuffer


@Singleton
class Brand2Service  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _brandTables = Tables.brandTables
  val _productTables = Tables.productTables
  val _repositoryTables = Tables.repositoryTables



  implicit private[this] val getResults1 = GetResult(r => (Product(r.<<[Option[Int]], r.<<[Int], r.<<[String])))
  implicit private[this] val getResults2 = GetResult(r => (Repository(r.<<[Option[Int]], r.<<[Int], r.<<[String], r.<<[Int])))
  implicit private[this] val getResults3 = GetResult(r => (Brand(r.<<[Option[Int]], r.<<[String])))
  implicit private[this] val getResults4 = GetResult(r => (
    Brand(r.<<[Option[Int]], r.<<[String]), 
    Repository(r.<<[Option[Int]], r.<<[Int], r.<<[String], r.<<[Int]), 
    r.<<[Option[Int]], r.<<[Option[Int]], r.<<[Option[String]]
  ))

  def getBrand(id: Int) {
    db.run(sql"select * from brand where id=#$id".as[(Int, String)]).map {
      result => result
    }
  }
  def getBrandHeadName(id: Int) : Future[String] = {
    db.run(sql"select * from brand where id=#$id".as[(Int, String)].headOption).map {
      result => result.map(_._2).getOrElse("")
    }
  }
      
  def fetchProductBranch1: Future[Seq[(Product, Int, String)]] = {
     db.run(sql"""
      select * from product
     """.as[(Product)]).map { products=>
       products.toSeq map {
         product => (product, 1, "aaa")
        }
     }  
  }

   def fetchProductBranch1_1: Future[Seq[(Product, Option[Int])]] = {
     db.run(sql"""
      select * from product
     """.as[(Product)]).map { products=>
       products.toSeq map {
         product => (product, Some(1))
        }
     }  
  }

  def fetchProductBranch2: Future[Seq[(Int, Int, Int)]] = {
    var cols =  Tables.productTables.baseTableRow.create_*.map(t => s"c.${t.name} AS c_${t.name}").toSeq.mkString(", ") 
    var tableName = Tables.productTables.baseTableRow.tableName
    println(cols)
    println(tableName)
    println(getClass.getSimpleName)
    val query = _productTables.groupBy(t=>(t.bid, t.id)).map {
      case (keys, groups) =>
        println("xxxxxxxxxxxxxxxxxxxx") 
        println(keys)
        println(groups)
        println("xxxxxxxxxxxxxxxxxxxx") 
        (keys._1, keys._2, groups.length)       
    }
    println(query.result.statements.head)
    db.run(query.result)   
  }


  def fetchProductBranch3: Future[Seq[(Int, Int, String)]] = {
    val list = Seq((1,1,"aaa"), (2,2,"bbb"))
    Future.successful(list)
  }


  def fetchProductBranch4(names: Seq[String]): Future[Seq[Product]] = {
    val query = _productTables.filter(t=>t.name inSetBind names)
    println(query.result.statements.head)
    db.run(query.result)
  }

  def putProduct(id: Int, name: String): Future[Unit] = {
    db.run(_productTables.filter(_.id === id).map(_.name).update(name)).map(_ => Unit)
  }

  def multipepost(names: Seq[String]): Future[Unit] = {
    db.run(DBIO.seq(names map { name => 
       _brandTables += Brand(None, name)
    }:_*).transactionally).map { _ => Unit }
  }
}