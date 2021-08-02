
import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONObject

case class User(name: String, num: Double) {}

object MultipleListaMerge2 {
    def main(args: Array[String]): Unit = {

        val a = List("a1", "a2")
        val b = List("b1", "b2")
        val c = List("c1", "c2")
        var d  = List(a,b,c)
        //var z : List[String] = List()

        val z: List[String] = d.reduce[List[String]]((_a, _b) => {
           var zz: List[String] = List();
          println(_a)
          println(_b)
          _a.flatMap { _aa =>
            _b.map { _bb =>
              zz = zz :+ ( _aa + "+" + _bb)
            }
          }
          println("xxxxx")
          zz
        })
        println(z)
        
       
    }
}