package cn.flipos.erp.productamount.impl.dto

import java.sql.Timestamp

case class BranchProductAmountChangeRecord(id: Long, branchId: Long, productId: Long, amount: Double, code: String,
                                           action: String, createdAt: Timestamp) {
}
