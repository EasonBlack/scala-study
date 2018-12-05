
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
    def bid = column[Int]("id")
    def name = column[String]("name")
    override def * = (id.?, bid.?, name) <> (Product.tupled, Product.unapply) 
  }

  val productTables = TableQuery[ProductTable]

  val test  = "aaaaaaa"

}