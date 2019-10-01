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
  def hello(id: String): ServiceCall[NotUsed, String]
 
  def postHello(id: String): ServiceCall[AddHelloRequest, Done]


  override final def descriptor = {
    import Service._
    // @formatter:off
    named("hello")
      .withCalls(
        pathCall("/api/hello/:id", hello _),
        restCall(Method.POST, "/api/hello/:id", postHello _),
      )
      .withAutoAcl(true)
  }
}

case class AddHelloRequest(name: String)

object AddHelloRequest {
  implicit val format: Format[AddHelloRequest] =
    Json.format[AddHelloRequest]
}


