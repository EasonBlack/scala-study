package services

import models._
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play
import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class RuleService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]  {
 
  val _ruleTables = Tables.ruleTables
  val _brandTables = Tables.brandTables
  val _ruleInfoTables = for {
    (rule, brand) <- _ruleTables join _brandTables on (_.brandid === _.id)
  } yield (rule, brand)

      
  def createRule(rule: Rule): Future[Int]  = {
    db.run(_ruleTables += rule)
  }

  def deleteRule(id: Int): Future[Int] = {
    db.run(_ruleTables.filter(_.id===id).delete)
  }

  def updateRule(id: Int, rule: Rule)  = {
    // val r = _ruleTables.filter(_.id===id).map(x => (x.name, x.operator, x.num))
    // db.run(r.update((rule.name, rule.operator, rule.num)))
    // val r = _ruleTables.filter(_.id===id)
    // db.run(r.update(rule))
    db.run(_ruleTables.insertOrUpdate(rule))
  }

  def fetchRulesByBrand(id: Int) : Future[Seq[Rule]] = {
    //db.run(_ruleTables.result)
    db.run(_ruleTables.filter(_.brandid===id).result)
  }

  def fetchRulesInfo(id: Int) : Future[List[RuleInfo]] = {
    db.run(_ruleInfoTables.filter(_._1.brandid===id).result) map {
      dataTuples  => {
        dataTuples map { d=>
          RuleInfo(
            d._1.id,
            d._1.name,
            d._1.operator,
            d._1.num,
            d._1.brandid,
            d._2
          )
        }
      }.toList
    } 
  }
   
}