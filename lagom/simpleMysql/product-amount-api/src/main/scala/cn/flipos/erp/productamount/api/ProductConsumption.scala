package test.erp.productamount.api

import play.api.libs.json.{Format, Json}

object ProductConsumption {
  implicit val format: Format[ProductConsumption] = Json.format
}

case class ProductConsumption(branchId: Long, productId: Long, amount: Double, orderNo: Option[String]) {
}
