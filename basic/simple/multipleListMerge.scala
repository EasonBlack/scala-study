
import scala.util.parsing.json.JSON
import scala.util.parsing.json.JSONObject
import scala.collection.mutable.ArrayBuffer

case class User(name: String, num: Double) {}

object MultipleListMerge3 {

 

   def main(args: Array[String]): Unit = {

        def crossJoin[T](list: Traversable[Traversable[T]]): Traversable[Traversable[T]] =
          list match {
            case xs :: Nil => xs map (Traversable(_))
            case x :: xs => for {
              i <- x
              j <- crossJoin(xs)
            } yield Traversable(i) ++ j
          }

        val a = List(User("a1", 1), User("a2", 2))
        val b = List(User("b1", 3), User("b2", 4))
        val c = List(User("c1", 5), User("c2", 6))
        var d  = List(a, b, c)
        println(crossJoin(d))
      
    }

}

object MultipleListMerge2 {
   

    def main(args: Array[String]): Unit = {

        val a = List(User("a1", 1), User("a2", 2)).map { r =>
          (r.name, r.num.toString)
        }
        val b = List(User("b1", 3), User("b2", 4)).map { r =>
          (r.name, r.num.toString)
        }
        val c = List(User("c1", 5), User("c2", 6)).map { r =>
          (r.name, r.num.toString)
        }
        var d  = List(a,b,c)
        val z: List[(String, String)] = d.reduce((_a, _b) => {
           var zz: List[(String, String)] = List.empty[(String, String)];
         
          _a.flatMap { _aa =>
            _b.map { _bb =>
              zz = zz :+ ( _aa._1 + "+" + _bb._1, _aa._2 + "+" + _bb._2 )
            }
          }
         
          zz
        })
        println(z)

    }
}

object MultipleListMerge1 {
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

