package test.erp.productamount.api

import play.api.libs.json.{Format, Json}

object ProductUpdate {
  implicit val format: Format[ProductUpdate] = Json.format
}

case class ProductUpdate(branchId: Long, productId: Long, amount: Option[Double]) {
}
