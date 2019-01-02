package org.example.hello.impl.dao

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import com.lightbend.lagom.scaladsl.persistence.EventStreamElement
import slick.dbio.{DBIOAction, NoStream}
import slick.lifted.Ordered
import slick.jdbc.GetResult

import scala.concurrent.{ExecutionContext, Future}

import scala.collection.JavaConverters
import scala.collection.mutable.ListBuffer

import org.example.hello.api.Brand

import akka.NotUsed

class BrandService(val db: Database)(implicit ec: ExecutionContext)  {
  val _brandTables = Tables.brandTables

  def getBrands: Future[Seq[Brand]] =
     db.run(_brandTables.result)

  def postBrand(name: String) : Future[NotUsed] = 
    db.run(_brandTables += Brand(0, name)).map {_ => NotUsed}

  def putBrand(id: Int, name: String) : Future[NotUsed] = 
    db.run(_brandTables.filter(_.id===id).map(_.name).update(name)).map {_ => NotUsed}
}