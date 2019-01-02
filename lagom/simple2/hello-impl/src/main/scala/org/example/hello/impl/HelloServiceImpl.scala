package org.example.hello.impl

import org.example.hello.api
import org.example.hello.api.{HelloService}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.api.broker.Topic
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
import scala.concurrent.Future

import akka.NotUsed
import org.example.hello.api._
import org.example.hello.impl.dao._
/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl(persistentEntityRegistry: PersistentEntityRegistry, brandService: BrandService) extends HelloService {


  override def hello(id: String) = ServiceCall { _ =>
     Future.successful(s"Hello world!")
  }

   override def hello2(id: String) = ServiceCall { _ =>
    //Future.successful(s"Hello world! $id")   
    val ref = persistentEntityRegistry.refFor[HelloEntity](id+"aaa")
    ref.ask(Hello(id))
  }
   override def hello3(id: String, name: String) = ServiceCall { _ =>
    //Future.successful(s"Hello world! $id, $name")   
    val ref = persistentEntityRegistry.refFor[HelloEntity](id)
    ref.ask(Hello3(id, name))
  }

  override def getBrands: ServiceCall[NotUsed, Seq[Brand]] =  {
    request => brandService.getBrands
  }

  override def postBrand : ServiceCall[Brand, NotUsed] = {
    request =>
    println("xxxxxxxxxxxxxxx")
    println(request)
    println("xxxxxxxxxxxxxxx")
    //Future.successful(NotUsed) 
    brandService.postBrand(request.name)
  }


  override def putBrand(id: Int) : ServiceCall[Brand, NotUsed] = {
    request =>
    println("xxxxxxxxxxxxxxx")
    println(request)
    println("xxxxxxxxxxxxxxx")
    //Future.successful(NotUsed) 
    brandService.putBrand(id, request.name)
  }

}
