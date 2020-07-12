


package models
import play.api.libs.json._

object Product2 {
	implicit val format: Format[Product2] = Json.format
}
case class Product2(id: Int, bid: Int,  extraInfo: Product2ExtraInfo)


object Product2ExtraInfo {
	implicit val format: Format[Product2ExtraInfo] = Json.format
}
case class Product2ExtraInfo(name: String)