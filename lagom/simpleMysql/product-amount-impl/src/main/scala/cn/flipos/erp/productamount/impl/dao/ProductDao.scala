package cn.flipos.erp.productamount.impl.dao

import java.sql.{SQLIntegrityConstraintViolationException, Timestamp}

import cn.flipos.dao.slick.SlickMySqlDaoComponent
import cn.flipos.erp.productamount.api.BranchProduct
import cn.flipos.erp.productamount.impl.dto.BranchProductAmountChangeRecord
import play.api.Logger
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ProductDao(val db: Database)(implicit val exec: ExecutionContext) extends SlickMySqlDaoComponent {

  private[this] val logger = Logger(getClass.getSimpleName)

  private[this] val branchProductTables = Tables.branchProductTables
  private[this] val changeRecordTables = Tables.branchProductAmountChangeRecordTables

  def findAll: Future[Seq[BranchProduct]] = runDBAction(branchProductTables.result)

  def findByBranch(branchId: Long): Future[Seq[BranchProduct]] =
    runDBAction(branchProductTables.filter(_.branchId === branchId).result)


  def saveOrUpdate(branchId: Long, productId: Long, amount: Double, code: String, action: String) = {
    val now = new Timestamp(System.currentTimeMillis())
    val record = BranchProductAmountChangeRecord(0L, branchId, productId, amount, code, action, now)
    (changeRecordTables += record).asTry.flatMap {
      case Failure(_: SQLIntegrityConstraintViolationException) =>
        logger.warn(s"The $record already exists.")
        DBIO.successful(Unit)
      case Failure(ex) => throw ex
      case Success(_) =>
        sqlu"""UPDATE #${branchProductTables.baseTableRow.tableName} SET amount = $amount, updated_at = $now
              WHERE branch_id = $branchId AND product_id = $productId""".flatMap {
        case 0 => branchProductTables += BranchProduct(branchId, productId, amount, now)
        case n => DBIO.successful(n)
      }
    }
  }

  def consume(branchId: Long, productId: Long, amount: Double, code: String) = {
    val now = new Timestamp(System.currentTimeMillis())
    val record = BranchProductAmountChangeRecord(0L, branchId, productId, amount, code, "CONSUME", now)
    (changeRecordTables += record).asTry.flatMap {
      case Failure(_: SQLIntegrityConstraintViolationException) =>
        logger.warn(s"The $record already exists.")
        DBIO.successful(Unit)
      case Failure(ex) => throw ex
      case Success(_) =>
        sqlu"""UPDATE #${branchProductTables.baseTableRow.tableName} SET amount = amount - $amount, updated_at = $now
              WHERE branch_id = $branchId AND product_id = $productId"""
    }
  }

  def delete(branchId: Long, productId: Long) =
    branchProductTables.filter(t => t.branchId === branchId && t.productId === productId).delete
}
