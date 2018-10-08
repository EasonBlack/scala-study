package eason

import eason.TestStore
import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._


object Tables  {
  class TestStoreTable(tag: Tag) extends Table[(Int , String)](tag, "teststore") {
    def id = column[Int]("id", O.PrimaryKey) 
    def name = column[String]("name") 
    def * = (id, name)
    // override def * = (id, name) <> (TestStore.tupled, TestStore.unapply)
  }

  val testStoreTables = TableQuery[TestStoreTable]
}