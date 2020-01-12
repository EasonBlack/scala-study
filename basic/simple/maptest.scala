
case class User(id: Int, name: String) {}

object HelloWorld {
    def main(args: Array[String]): Unit = {
        var list = List(User(1,"a"), User(2, "b"))
        val m = list.groupBy(_.id).map { case (id, item) => 
          (id, item.head)
        }
        println(list)
        test(3, m)
    }

    def test(id: Int, oMap: Map[Int, User]) = {
      println(oMap.getOrElse(id, User(99, "test")))
    }
}

