package utils

import java.sql.Timestamp

import scala.concurrent.duration._
import play.api.libs.json._

object Constant { 

  def resetData(num: Int)  = {
    Json.obj(
      "a" -> JsString("12"),
      "b" -> JsString("22"),
      "c" -> JsString(num.toString)
    )
  }
    

  val data = Map(
    "a" -> 1,
    "b" -> 2,
    "c" -> 3,
    "d" -> 4,
    "e" -> 10
  )


  val data1 = Json.obj(
    "a" -> JsString("1"),
    "b" -> JsString("2"),
    "c" -> resetData(3)  ,
    "d" -> JsArray()
  )

  val data2 = Map(
    "a" -> Json.obj(
      "a" -> JsString("1"),
      "b" -> JsString("2"),
      "c" -> JsString("3")
    ),
    "b" -> Json.obj(
      "a" -> JsString("12"),
      "b" -> JsString("22"),
      "c" -> JsString("32")
    ),
    "c" -> Json.obj(
      "a"->JsString("12"),
      "b" -> JsArray(
        Seq(Json.obj(
          "id" -> 1,
          "name" -> "a"
        ),
        Json.obj(
          "id" -> 2,
          "name" -> "b"
        ))
      )
    )
  )


  val data3 = Map(
    "100" -> Json.obj(
      "2019-01-01"-> Json.obj(
        "a" -> 1
      ),
       "2019-01-01"-> Json.obj(
        "a" -> 1
      )
    )
  )


 
}