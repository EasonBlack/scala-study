package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import models._
import services._
import services.MongoFactory
import play.api.cache.SyncCacheApi
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import play.api.mvc.{InjectedController, Request, Result}

import com.mongodb.casbah.Imports._


@Singleton
class MongoController @Inject()(cc: ControllerComponents  )
(implicit exec: ExecutionContext) extends AbstractController(cc) {

	val mongoClient = MongoClient("localhost", 27017)
	val db = mongoClient("nice")


	def fetchFromMongoStock = Action.async { request =>
		println(db.collectionNames)
		val coll = db("test01s")
  
		val ccc = MongoFactory.mongoDB("test")
		val allDocs = ccc.find()
		println(ccc)
    println( allDocs )
		for(doc <- allDocs) println( doc )
		Future(Ok("GET"))
  }	

	def setMongoStockTest = Action.async { request =>
		// val coll = db("test01s")
		// val a = MongoDBObject("hello" -> "world")
		// val b = MongoDBObject("language" -> "scala")
		// coll.insert( a )
		// coll.insert( b )
		val col = MongoFactory.mongoDB("test")
		val a = MongoDBObject("hello" -> "world")
		val b = MongoDBObject("language" -> "scala")
		col.insert( a )
		col.insert( b )
		Future(Ok("GET"))
  }	


}