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

}
