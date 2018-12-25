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

@Singleton
class BrandService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _brandTables = Tables.brandTables
  val _productTables = Tables.productTables
  val _repositoryTables = Tables.repositoryTables

  println(Tables.test);

  implicit private[this] val getResults1 = GetResult(r => (Product(r.<<[Option[Int]], r.<<[Int], r.<<[String])))
  implicit private[this] val getResults2 = GetResult(r => (Repository(r.<<[Option[Int]], r.<<[Int], r.<<[String])))
  implicit private[this] val getResults3 = GetResult(r => (Brand(r.<<[Option[Int]], r.<<[String])))
  implicit private[this] val getResults4 = GetResult(r => (
    Brand(r.<<[Option[Int]], r.<<[String]), 
    Repository(r.<<[Option[Int]], r.<<[Int], r.<<[String]), 
    r.<<[Option[Int]], r.<<[Option[Int]], r.<<[Option[String]]
  ))

      
  def fetchAll(): Future[Seq[Brand]] = {
    db.run(_brandTables.filter(_.id=!= -1).result)
  }


  def fetchAllProduct: Future[Seq[Product]] = {
    // db.run(_productTables.result)
    // 需要 (implicit ec: ExecutionContext)
    db.run(_productTables.result).map {
      result => result
    }
  }

  def fetchAllProduct2: Future[Seq[Product]] = {
    //# option 1
    // db.run(sql"select * from product".as[Product])

    //# option 2
    // db.run(sql"select * from product".as[Product]).map { result => 
    //   result
    // }
    
    //# option 3
    // db.run(sql"select * from product".as[Product]).map { results => 
    //   results map {
    //     result=> (result)
    //   }
    // }

    //
    db.run(sql"select * from product".as[Product]).map { results =>
      val a  = 1
      results.filter(_.id.get > a) 
    }
  }

  def fetchProductBranch: Future[Seq[(Product, Brand)]] = {
    // #1
    // db.run((_productTables join _brandTables on (_.bid === _.id)).result).map { result =>
    //   println(result)
    //   result
    // }

    // #2 下面这样就可以
    // db.run((_productTables join _brandTables on (_.bid === _.id)).result)

    //
    db.run(sql"select * from product".as[Product]).flatMap { products =>
      db.run(sql"select * from brand".as[Brand]).map { brands =>
        println(products)
        println(brands)  
        products.toSeq map { product =>
          val productId = product.bid
          println(productId)
          val brand = brands.toSeq.filter(_.id.get == product.bid).head
          (product, brand)
            
        }
      }
    }
  }

  def fetchProductBranch2: Future[Seq[(Product, String)]] = {
    val query = (_productTables join _brandTables on (_.bid === _.id))
    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    println(query.result.statements.head)
    println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    db.run(query.result).map { 
      result => result.map (
        item => (item._1, item._2.name)
      )
    }
  }

  def fetchProductBranch3: Future[Seq[(Brand, Repository, Option[Product])]] = {
    db.run(
      (
        _brandTables 
        join _repositoryTables on (_.id === _.brandId)
        joinLeft _productTables on (_._1.id === _.bid)
      ).result)
      .map {  result =>
        println("get originnal data")
        println(result)
        result map {
           case((brand, repository), product) => (brand, repository, product)
        }
      } 
  }

  def fetchProductBranch4: Future[Seq[(Brand, Repository, Option[Product])]] = {
    val query = for {
      ((brand,repository), product) <- 
        _brandTables
        .join(_repositoryTables).on(_.id === _.brandId)
        .joinLeft(_productTables).on(_._1.id === _.bid)
    } yield (brand, repository, product)
    db.run(query.result)
  }

   def fetchProductBranch5: Future[Seq[(String, String, Option[String])]] = {
     db.run(sql"""
      select b.name,r.name,p.name from brand b 
      join repository r on b.id = r.brandId
      left join product p  on b.id = p.bid
     """.as[(String, String, Option[String])])
  }

   def fetchProductBranch6: Future[Seq[(Brand, Repository, Option[Int], Option[Int], Option[String])]] = {
     db.run(sql"""
      select b.*,r.*,p.* from brand b 
      join repository r on b.id = r.brandId
      left join product p  on b.id = p.bid
     """.as[(Brand, Repository, Option[Int], Option[Int], Option[String])])
  }


  def fetchProductBranch7: Future[Seq[(Brand, Seq[Option[Product]])]] = {
    db.run(
        (
          _brandTables
          joinLeft _productTables on (_.id === _.bid)
        )
        .result
    )
    .map(result => 
      result.groupBy(_._1.id).map { case (_, items) =>
        println(items)
        println(items.map(_._2))
        println(items.head._1)
        val brand = items.head._1
        (brand, items.map(_._2))
      }.toSeq
    ) 
    
  }


   def fetchProductBranch9: Future[Seq[(Int, Int)]] = {
      // #1 
      db.run(_productTables.groupBy(_.bid).map {
       case (bid, products) => (bid, products.length)
      }.result) 

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