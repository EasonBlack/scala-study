package services

import java.sql.{Date, Timestamp}
import entities._

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{TableQuery, Tag}


object Tables {
  class Test1DemoTable(tag: Tag) extends Table[Test1Demo](tag, "demo") {
    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    override def * = (id, name) <> (Test1Demo.tupled, Test1Demo.unapply)
  }
  val test1DemoTables = TableQuery[Test1DemoTable]

  class Test2DemoTable(tag: Tag) extends Table[Test2Demo](tag, "demo") {
    def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
    def name = column[String]("name")
    override def * = (id, name) <> (Test2Demo.tupled, Test2Demo.unapply)
  }
  val test2DemoTables = TableQuery[Test2DemoTable]
}