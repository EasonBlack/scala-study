package utils

import models._
import play.api.libs.json._

object JsonUtils {

  implicit val brandWrites = Json.writes[Brand]

  implicit val ruleWrites = Json.writes[Rule]
  implicit val ruleReads = Json.reads[Rule]
  implicit val ruleInfoWrites = Json.writes[RuleInfo]

  //implicit val categoryWrites = Json.writes[Category]
  implicit val categoryFormat = Json.format[Category]
}