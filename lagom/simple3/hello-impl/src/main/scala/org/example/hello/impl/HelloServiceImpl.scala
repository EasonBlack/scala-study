package org.example.hello.impl

import org.example.hello.api
import org.example.hello.api.{HelloService, AddHelloRequest}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import scala.concurrent.Future

import akka.{NotUsed, Done}
/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends HelloService {


  override def hello(id: String):ServiceCall[NotUsed, List[String]] = ServiceCall { _ =>
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    ref.ask(GetHello())
  }

  override def postHello(id: String): ServiceCall[AddHelloRequest, Done] = ServiceCall { request=>
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    ref.ask(AddToHello(request.name))
  }

}
