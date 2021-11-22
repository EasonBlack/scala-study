  
package services

import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah._

object MongoFactory {

  private val SERVER = "localhost"
  private val PORT   = 27017
  private val DATABASE = "xxxx"
  private val COLLECTION = "stocks"

  val mongoClient  = MongoClient(SERVER, PORT)
  val mongoDB = mongoClient(DATABASE)

}