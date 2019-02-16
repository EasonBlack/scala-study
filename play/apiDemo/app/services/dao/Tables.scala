
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


  class OrderTable(tag: Tag) extends Table[Order](tag, "order") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    override def * = (id, name) <> (Order.tupled, Order.unapply) 
  }

  val orderTables = TableQuery[OrderTable]

  class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "orderitem") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def orderId = column[Int]("orderId")
    def name = column[String]("name")
    override def * = (id, orderId, name) <> (OrderItem.tupled, OrderItem.unapply) 
  }

  val orderItemTables = TableQuery[OrderItemTable]

  class OrderItemAccountTable(tag: Tag) extends Table[OrderItemAccount](tag, "orderitemaccount") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def orderItemId = column[Int]("orderItemId")
    def name = column[String]("name")
    override def * = (id, orderItemId, name) <> (OrderItemAccount.tupled, OrderItemAccount.unapply) 
  }

  val orderItemAccountTables = TableQuery[OrderItemAccountTable]

  //OrderRelate1
  class OrderRelate1Table(tag: Tag) extends Table[OrderRelate1](tag, "orderrelate1") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def orderId = column[Int]("orderId")
    def relateName = column[String]("relateName")
    override def * = (id, orderId, relateName) <> (OrderRelate1.tupled, OrderRelate1.unapply) 
  }

  val orderRelate1Tables = TableQuery[OrderRelate1Table]

  class OrderRelate2Table(tag: Tag) extends Table[OrderRelate2](tag, "orderrelate2") {
    def id = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def orderRelateId = column[Int]("orderRelateId")
    def relateName = column[String]("relateName")
    override def * = (id, orderRelateId, relateName) <> (OrderRelate2.tupled, OrderRelate2.unapply) 
  }

  val orderRelate2Tables = TableQuery[OrderRelate2Table]


  val test  = "aaaaaaa"

}