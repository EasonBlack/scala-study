
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
}