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


case class UserA(id: Int, name: String, age: Int)
case class UserB(id: Int, name: String)

trait Foo {
   type T;
   val x:T;
   def getX:T = x
}

class Bar {
  def apply() = println("this is bar")
}

case class Account(id: Long, name: String){}

@Singleton
class DemoController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    //tuple
    var t1 = (1, "a", "aaaa")
    println(t1._1 + ":" + t1._2 + ":" + t1._3)
    println("===================")
    //list
    var l1 = "a"::"b"::Nil
    var l2 = List("a", "b")
    var l3 = l1:::l2
    var l4 = l1 ++ l2
    println(l1)
    println(l2)
    println(l3)
    println(l4)
    println("====================")
    class Test {
      def show(i: Int) =  i+10  
    }
    var t = new Test;
    println(t show(12))
    println("====================")
    var users : Seq[UserA] =  Seq()
    // var users : List[UserA] =  List()  same
    users = users :+ UserA(1,"a", 1)
    users = users :+ UserA(2,"b", 1)
    users = users :+ UserA(3,"c", 1)
    println(users);
    println(users.headOption)
    println("====================")
    var __x = (new Foo{type T = Int; val x = 123}).getX
    var __y = (new Foo{type T = String;val x = "hello tony"}).getX
    println(__x + __y);
    println("====================")
    val bar = new Bar
    bar()
    println("====================")
    var _a = Account(1L, "aaa")
    var __a =  testAccount(_a.id, _a.name)
    Ok("test")
  }

  def testAccount(id: Long, name: String) = {
    id.toString + name.toString
  }

  def testRequest(name: String) = Action { implicit request: Request[AnyContent] =>
    println(request.queryString)
    Ok(Json.toJson(request.queryString))
  }


}
