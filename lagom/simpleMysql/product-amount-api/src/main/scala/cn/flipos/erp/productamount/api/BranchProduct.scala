package test.erp.productamount.api

import java.util.Date

import play.api.libs.json.{Format, Json}

object BranchProduct {
  implicit val format: Format[BranchProduct] = Json.format
}

case class BranchProduct(branchId: Long, productId: Long, amount: Double, updatedAt: Date) {
}
