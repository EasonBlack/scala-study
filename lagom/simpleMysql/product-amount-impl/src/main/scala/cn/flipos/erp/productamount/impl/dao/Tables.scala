package cn.flipos.erp.productamount.impl.dao

import java.sql.Timestamp
import java.util.Date

import cn.flipos.dao.slick.DaoUtils._
import cn.flipos.dao.slick.IdTable
import cn.flipos.erp.productamount.api.BranchProduct
import cn.flipos.erp.productamount.impl.dto.BranchProductAmountChangeRecord
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

object Tables {

  class BranchProductTable(tag: Tag) extends Table[BranchProduct](tag, "t_branch_product_amount") {

    def branchId = column[Long]("branch_id")

    def productId = column[Long]("product_id")

    def amount = column[Double]("amount")

    def updatedAt = column[Date]("updated_at")

    override def * = (branchId, productId, amount, updatedAt) <>
      ((BranchProduct.apply _).tupled, BranchProduct.unapply)
  }

  lazy val branchProductTables = TableQuery[BranchProductTable]

  class BranchProductAmountChangeRecordTable(tag: Tag) extends IdTable[BranchProductAmountChangeRecord](tag, "t_product_consumption_record") {

    def branchId = column[Long]("branch_id")

    def productId = column[Long]("product_id")

    def amount = column[Double]("amount")

    def code = column[String]("code")

    def action = column[String]("action")

    def createdAt = column[Timestamp]("created_at")

    override def * = (id, branchId, productId, amount, code, action, createdAt) <>
      (BranchProductAmountChangeRecord.tupled, BranchProductAmountChangeRecord.unapply)
  }

  lazy val branchProductAmountChangeRecordTables = TableQuery[BranchProductAmountChangeRecordTable]
}
