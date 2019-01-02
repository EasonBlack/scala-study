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
  def hello2(id: String): ServiceCall[NotUsed, String]
  def hello3(id: String, name: String): ServiceCall[NotUsed, String]
  def getBrands: ServiceCall[NotUsed, Seq[Brand]]  
  def postBrand: ServiceCall[Brand, NotUsed]  
  def putBrand(id: Int): ServiceCall[Brand, NotUsed]  

  override final def descriptor = {
    import Service._
    // @formatter:off
    named("hello")
      .withCalls(
        pathCall("/api/hello/:id", hello _),
        pathCall("/api/hello2/:id", hello2 _),
        pathCall("/api/hello3/:id/:name", hello3 _),
        pathCall("/api/brand", getBrands _),
        restCall(Method.POST, "/api/brand2", postBrand _),
        restCall(Method.PUT,  "/api/brand3/:id", putBrand _),
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

