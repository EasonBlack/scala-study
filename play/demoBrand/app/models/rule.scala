package models

import models._

case class Rule(id: Option[Int], name: String,  operator: String, num: String, brandid: Int)

case class RuleInfo(id: Option[Int], name: String,  operator: String, num: String, brandid: Int, 
                    brandInfo: Brand)