package org.example.hello.impl

import org.example.hello.api
import org.example.hello.api.{HelloService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.broker.TopicProducer
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import scala.concurrent.Future
/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends HelloService {


  override def hello(id: String) = ServiceCall { _ =>
     Future.successful(s"Hello world!")
  }

   override def hello2(id: String) = ServiceCall { _ =>
    //Future.successful(s"Hello world! $id")   
    val ref = persistentEntityRegistry.refFor[HelloEntity]("aaa")
    ref.ask(Hello(id))
  }
   override def hello3(id: String, name: String) = ServiceCall { _ =>
    //Future.successful(s"Hello world! $id, $name")   
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    ref.ask(Hello3(id, name))
  }

}
