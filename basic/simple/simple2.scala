// scala simple2.scala HelloWorld

object HelloWorld {
    def main(args: Array[String]): Unit = {
         var a = Vector(1)
         var b = Seq(2,1,3)
         a.toList match {
             //case x::xs => println(x + " " + xs)
             case (x::List()) => println(x + " ")
             case xx => println("xxx" + xx.toString)
         }

          b match {
             case (x::Seq()) => println(x + " ")
             case (x::xs) => println(x + " " + xs)
             case xx => println("xxx" + xx.toString)
         }
         println(a.toSeq)
         var c : Seq[Int] = a.toSeq;
         println(c)

        var dflag =   Some("bb")
        var d = "aaaa"
        d += (if(dflag.isDefined) dflag.get else "" )
        println(d)
    }
}