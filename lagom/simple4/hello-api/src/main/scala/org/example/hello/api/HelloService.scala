package org.example.hello.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}
import com.lightbend.lagom.scaladsl.api.transport.Method

object HelloService  {
  val TOPIC_NAME = "greetings"
}


trait HelloService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def hello(id: String): ServiceCall[NotUsed, List[String]]

  def hello2(): ServiceCall[NotUsed, String]
  def hello3(): ServiceCall[NotUsed, String]
 
  def postHello(id: String): ServiceCall[AddHelloRequest, Done]

  def addToCart(id: String): ServiceCall[AddToCartRequest, Done]
  def removeFromCart(id: String): ServiceCall[RemoveFromCartRequest, Done]

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("hello")
      .withCalls(
        pathCall("/api/hello/:id", hello _),
        pathCall("/api/hello2", hello2 _),
        pathCall("/api/hello3", hello3 _),
        restCall(Method.POST, "/api/hello/:id", postHello _),
        restCall(Method.POST, "/api/add-to-cart/:id", addToCart _),
        restCall(Method.DELETE, "/api/cart/:id", removeFromCart _)
      )
      .withAutoAcl(true)
  }
}

case class AddHelloRequest(name: String)

object AddHelloRequest {
  implicit val format: Format[AddHelloRequest] =
    Json.format[AddHelloRequest]
}

case class AddToCartRequest(product: String)
object AddToCartRequest {
  implicit val format: Format[AddToCartRequest] =
    Json.format[AddToCartRequest]
}


case class RemoveFromCartRequest(product: String)
object RemoveFromCartRequest {
  implicit val format: Format[RemoveFromCartRequest] = Json.format[RemoveFromCartRequest]
}

