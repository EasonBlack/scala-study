package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import models._
import services._
import play.api.cache.SyncCacheApi
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

@Singleton
class CacheController @Inject()(cc: ControllerComponents, cacheApi: SyncCacheApi)
(implicit exec: ExecutionContext) extends AbstractController(cc) {

	def fetchFromCache = Action.async { request =>

		var a = cacheApi.get[String]("test99")
		var b = cacheApi.get[(String, String)]("test99")
		println(a)
		println(b)
		a match {
			case None => 	Future.successful(Ok("NO CACHE"))
			case Some(v) => Future.successful(Ok(v))
		}
	

  }

	def postToCache = Action.async(parse.json) { request => 
		val test = (request.body \ "test").asOpt[String]
		println(test)
		cacheApi.set("test99", test.get, 3.minutes)
		Future(Ok("GET"))
	}
	def postToCache2 = Action.async(parse.json) { request => 
		val test = (request.body \ "test").asOpt[String]
		val test2 = (request.body \ "test2").asOpt[String]
		println((test.get, test2.get))
		cacheApi.set("test99", (test.get, test2.get), 3.minutes)
		Future(Ok("GET"))
	}
}