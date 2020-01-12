package test.erp.productamount.api

import play.api.libs.json.{Format, Json}

object ProductConsumptionResult {
  implicit val format: Format[ProductConsumptionResult] = Json.format
}

case class ProductConsumptionResult(branchId: Long, productId: Long, amountAfter: Double) {
}
