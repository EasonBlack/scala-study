
package services

import entities._
import services._

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play

import slick.jdbc.{GetResult}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class Test1DemoService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
 
  val _test1DemoTables = Tables.test1DemoTables

      
  def fetchTest1Demo(): Future[Seq[Test1Demo]] = {
    db.run(_test1DemoTables.result)
  }


   
}