package org.example.hello.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.api.broker.kafka.{KafkaProperties, PartitionKeyStrategy}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

object HelloService  {
  val TOPIC_NAME = "greetings"
}


trait HelloService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def hello(id: String): ServiceCall[NotUsed, String]



  override final def descriptor = {
    import Service._
    // @formatter:off
    named("hello")
      .withCalls(
        pathCall("/api/hello/:id", hello _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

