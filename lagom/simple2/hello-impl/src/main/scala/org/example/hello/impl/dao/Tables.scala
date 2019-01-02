
package org.example.hello.impl.dao

import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._
import org.example.hello.api._

object Tables  {
  class BrandTable(tag: Tag) extends Table[Brand](tag, "brand") {
    def id = column[Int]("id")
    def name = column[String]("name")
    override def * = (id, name) <> ((Brand.apply _).tupled, Brand.unapply)
  }

  val brandTables = TableQuery[BrandTable]

}