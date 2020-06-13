package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.http.HttpEntity
import akka.util.ByteString
import play.api.libs.json._
import play.api.mvc.AnyContent

import org.json4s._
import org.json4s.jackson.JsonMethods._
import scala.collection.mutable.MutableList
import models.Student



case class UserAA(id: Int, name: String, age: Int)
case class Lesson(id: Int, name: String)
case class UserBB(id: Int, name: String, lessons: Seq[Lesson])


@Singleton
class JsonHandleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val formats = DefaultFormats
  def test1() = Action { implicit request: Request[AnyContent] =>
    val json = org.json4s.jackson.JsonMethods.parse(""" 
      { 
        "id": 1,
        "name": "aaaa",
        "age": 11
      }
    """)

    val josn2 = org.json4s.jackson.JsonMethods.parse("""  
      {
        "id": 2, 
        "name": "bbbbb",
        "lessons": [
          {"id": 1, "name": "aaaa"},
          {"id": 2, "name": "bbbb"}
        ]
      }
    """)
    val ua: UserAA = json.extract[UserAA]
    val ub: UserBB = josn2.extract[UserBB]
    println(ua)
    println(ub)
    Ok("test")
  }


   def test2() = Action { implicit request: Request[AnyContent] =>
      val json1 =Json.parse("""  
      {
        "id": 2, 
        "name": "bbbbb",
        "lessons": [
          {"id": 1, "name": "aaaa"},
          {"id": 2, "name": "bbbb"}
        ],
        "config": {
          "productId": 1,
          "banner": {
            "path": "aaaaaa"
          }
        }
      }
      """)

      println(json1)
      var _name = (json1 \ "name")
      var _config = (json1 \ "config")
      println(_name.as[String])
      println(_config)
      var a = Json.obj(
        "productId" -> (_config \ "productId").as[Int],
        "banner" -> Json.obj(
          "path" -> (_config \ "banner" \ "path").as[String],
        )
      )
      var b = Json.obj(
        "productId" -> 3,
        "banner" -> Json.obj(
          "path" -> (_config \ "banner" \ "path").as[String],
        )
      )
      val updatedJson = json1.as[JsObject] + ("config" -> b)
      println(a)
      println(a.toString)
      println(updatedJson)
      println(Json.parse(a.toString))

     
      Ok("test2")
   }


    def test3() = Action { implicit request: Request[AnyContent] =>
       val json1 =Json.parse("""[
         {"id": 1},
         {"id": 2},
         {"id": 3}
       ]""")
       println(json1)
       json1 match  {
         case js: JsArray => js.value.map {
           case j => println((j \ "id").as[Int])
         }
        case _ => 
       }
      
       Ok("test3")
    }

    def test4() = Action { implicit request: Request[AnyContent] =>

      val listResult = MutableList[Student]()
      val list = MutableList[Student]()
      list.+=(Student(1,  """[{"name": "a" }, { "name": "b" }]""", "20"))
      list.+=(Student(2,  """[{"name": "c" }, { "name": "d" }]""", "30"))
     
      list map { case student => 
        Json.parse(student.name) match {
          case js : JsArray => js.value.map {
            case j => {
              val _student = student.copy(name = (j \ "name").as[String])
              listResult.+=(_student)
            }
          }
        }
      }
     
      println(listResult)
      
      Ok("test4")
    }

    def test5() = Action { implicit request: Request[AnyContent] =>

      val listResult = MutableList[(Int, String, String)]()
      val list = MutableList[Student]()
      list.+=(Student(1,  """[{"name": "a" }, { "name": "b" }]""", "20"))
      list.+=(Student(2,  """[{"name": "c" }, { "name": "d" }]""", "30"))
     
      list map { case student => 
        Json.parse(student.name) match {
          case js : JsArray => js.value.map {
            case j => {
              val _student = ( student.id, (j \ "name").as[String], student.age)
              listResult.+=(_student)
            }
          }
        }
      }
     
      println(listResult.toList)
      listResult.groupBy(t=>t._1).map {
        case (studentId, items) => { 
          println(studentId)
          println(items)
        }
      }
      
      Ok("test5")
    }



}
