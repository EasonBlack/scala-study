
import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONObject

case class User(name: String, num: Double) {}

object JsonToClass {
    def main(args: Array[String]): Unit = {
        
        val jsonStr = """{"name":"Ricky", "num":  22}"""
        val jsonValue = JSON.parseFull(jsonStr)
        println(jsonValue)
        val u = jsonValue.get.asInstanceOf[Map[String, String]]
        //val u = User(xxx.get("name"), xxx.get("num").toInt)
        println(u)
        println(u.get("name"))
        println( u.get("num").getOrElse(0))
        val xxx  = User(u.get("name").getOrElse(""), u.get("num").getOrElse(0).asInstanceOf[Double])
        println(xxx)
    }
}