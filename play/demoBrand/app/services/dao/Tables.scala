
package services.dao

import models._
import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._


object Tables  {
  class BrandTable(tag: Tag) extends Table[Brand](tag, "brand") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    override  def * = (id, name) <> (Brand.tupled, Brand.unapply)
  }

   val brandTables = TableQuery[BrandTable]

  class CategoryTable(tag: Tag) extends Table[Category](tag, "category") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def brandid = column[Int]("brandid")
    override  def * = (id.?, name, brandid) <> (Category.tupled, Category.unapply)
  }

  val categoryTables = TableQuery[CategoryTable]

  class ProductTable(tag: Tag) extends Table[Product](tag, "product") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def categoryid = column[Int]("categoryid")
    override  def * = (id.?, name, categoryid) <> (Product.tupled, Product.unapply)
  }

  val productTables = TableQuery[ProductTable]


  class RuleTable(tag: Tag) extends Table[Rule](tag, "rule") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def operator = column[String]("operator")
    def num = column[String]("num")
    def brandid = column[Int]("brandid")
    override  def * = (id.?, name, operator, num, brandid) <> (Rule.tupled, Rule.unapply)
  }

  val ruleTables = TableQuery[RuleTable]

  class ShopTable(tag: Tag) extends Table[Shop](tag, "shop") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    def brandid = column[Int]("brandid")
    override  def * = (id, name, brandid) <> (Shop.tupled, Shop.unapply)
  }

  val shopTables = TableQuery[ShopTable]

}

