
package services

import entities._
import services._

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play
import play.api.db._

import scala.concurrent.{ExecutionContext, Future}


@Singleton
class Test2DemoService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
 
  val _test2DemoTables = Tables.test2DemoTables
  def dbTest2 = Database.forConfig("test2")
  
      
  def fetchTest2Demo(): Future[Seq[Test2Demo]] = {
    dbTest2.run(_test2DemoTables.result)
  }


   
}