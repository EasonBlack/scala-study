package org.example.hello.impl.dao

import java.sql.Timestamp
import java.util.Date

import slick.jdbc.MySQLProfile.api._
import slick.lifted.{TableQuery, Tag}



object Tables {

  class HelloTable(tag: Tag) extends Table[HelloDTO](tag, "hello") {

    def id = column[Long]("id")
    def name = column[String]("name")

    override def * = (id, name) <> (HelloDTO.tupled, HelloDTO.unapply)
  }

  val helloTables = TableQuery[HelloTable]
}