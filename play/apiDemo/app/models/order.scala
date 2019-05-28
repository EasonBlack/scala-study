
package models

case class Order(id: Int, name: String)
case class OrderItem(id: Int, orderId: Int,  name: String, no: Option[Int])
case class OrderItemAccount(id: Int, orderItemId: Int,  name: String)
case class OrderRelate1(id: Int, orderId: Int,  relateName: String)
case class OrderRelate2(id: Int, orderRelateId: Int,  relateName: String)