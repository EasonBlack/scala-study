
package services.dao

import models._
import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

object Tables  {
  class BrandTable(tag: Tag) extends Table[Brand](tag, "brand") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    override def * = (id.?, name) <> (Brand.tupled, Brand.unapply)
  }

  val brandTables = TableQuery[BrandTable]

  class ProductTable(tag: Tag) extends Table[Product](tag, "product") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def bid = column[Int]("bid")
    def name = column[String]("name")
    override def * = (id.?, bid, name) <> (Product.tupled, Product.unapply) 
  }

  val productTables = TableQuery[ProductTable]



  class RepositoryTable(tag: Tag) extends Table[Repository](tag, "repository") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def brandId = column[Int]("brandId")
    def name = column[String]("name")
    override def * = (id.?, brandId, name) <> (Repository.tupled, Repository.unapply) 
  }

  val repositoryTables = TableQuery[RepositoryTable]

  val test  = "aaaaaaa"

}