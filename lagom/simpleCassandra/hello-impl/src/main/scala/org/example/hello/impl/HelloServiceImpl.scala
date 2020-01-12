package org.example.hello.impl

import org.example.hello.api
import org.example.hello.api.{HelloService, AddHelloRequest, AddToCartRequest, RemoveFromCartRequest}
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

  override def hello2():ServiceCall[NotUsed, String] = ServiceCall { _ =>
    val ref = persistentEntityRegistry.refFor[HelloEntity]("99")
    ref.ask(GetHello2())
  }
  override def hello3():ServiceCall[NotUsed, String] = ServiceCall { _ =>
    persistentEntityRegistry.refFor[HelloEntity]("99").ask(GetHello3())
  }
  override def hello4(id: String):ServiceCall[NotUsed, String] = ServiceCall { _ =>
    persistentEntityRegistry.refFor[HelloEntity]("99").ask(GetHello2())
  }

  override def postHello(id: String): ServiceCall[AddHelloRequest, Done] = ServiceCall { request=>
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    println(request.name)  //post 中的body {"name": "asdfad"}
    ref.ask(AddToHello(request.name))
  }

  override def addToCart(id: String): ServiceCall[AddToCartRequest, Done] = ServiceCall { request =>
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    ref.ask(AddToCartCommand(request.product))
  }
  override def removeFromCart(id: String): ServiceCall[RemoveFromCartRequest, Done] = ServiceCall { request =>
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    ref.ask(RemoveFromCartCommand(request.product))
  }

}
