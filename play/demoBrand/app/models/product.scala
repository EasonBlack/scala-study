package models

case class Product(id: Option[Int], name: String, categoryid: Int)

case class ProductInfo(id: Option[Int], name: String, categoryid:Int, categoryInfo: Category, 
                    brandInfo: Brand)