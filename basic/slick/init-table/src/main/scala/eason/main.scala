
package eason


import slick.jdbc.MySQLProfile.api.{Database, _}
import eason.Tables._

object Ab extends App  {
	//println("Hello World")
	val db = Database.forConfig("mydb")

	val setup = DBIO.seq(
		
		(testStoreTables.schema).create,

		testStoreTables ++= Seq(
			(1, "aaaaa"),
			(2, "bbbbb")
		)

	)

	db.run(setup)
} 