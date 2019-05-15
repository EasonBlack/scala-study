package services

import models.{Order, OrderItem, OrderItemAccount, OrderRelate1, OrderRelate2}
import services.dao.Tables

import javax.inject._
import slick.jdbc.MySQLProfile.api._
import slick.driver.JdbcProfile
import scala.concurrent.Future
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.Play

import slick.jdbc.GetResult

import scala.concurrent.{ExecutionContext, Future}

import scala.collection.JavaConverters
import scala.collection.mutable.ListBuffer

@Singleton
class OrderService  @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile]  {
  val _orderTables = Tables.orderTables
  val _orderItemTables = Tables.orderItemTables
  val _orderItemAccountTables = Tables.orderItemAccountTables
  val _orderRelate1Tables = Tables.orderRelate1Tables
  val _orderRelate2Tables = Tables.orderRelate2Tables
  
  def fetchOrder: Future[Seq[(Order, Seq[OrderItem])]] = {
    val query = _orderTables
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_.id)
      val queryItem = _orderItemTables.filter(_.orderId.inSetBind(orderIds))

      db.run(queryItem.result)
      .map { items =>
         items.groupBy(_.orderId)
      }
      .map { orderItemsMap  =>
          println(orderItemsMap)    //Map(2 -> Vector(OrderItem(3,2,b1), OrderItem(4,2,b2)), 1 -> Vector(OrderItem(1,1,a1), OrderItem(2,1,a2)))
          orders.map(o => (o, orderItemsMap.getOrElse(o.id, Seq.empty[OrderItem])))
      }

    }  
  }

  def fetchOrder1_1: Future[Seq[(Order, Option[OrderItem])]] = {
    val query = (_orderTables joinLeft _orderItemTables on (_.id === _.orderId))
    db.run(query.result.map { results => 
    results.map { 
        case (order, item) => 
        (order, item) 
      }
    })  
  }

  def fetchOrder1_2: Future[Seq[(Order, Seq[Option[OrderItem]])]] = {
    val query = (_orderTables joinLeft _orderItemTables on (_.id === _.orderId))
    db.run(query.result)
    .map { items => 
      items.groupBy(_._1.id)
      .map { case (_, items) => 
        (items.head._1, items.map(_._2))
      }.toSeq
    }
  }



  def fetchOrder2: Future[Seq[(OrderItem, Option[OrderItemAccount])]] = {
    val query = _orderTables
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_.id)
      val queryItem = (_orderItemTables joinLeft _orderItemAccountTables on (_.id===_.orderItemId)).filter(_._1.orderId.inSetBind(orderIds))
      db.run(queryItem.result)
      .map { items => 
        items.map {
          case (item, account) => 
            (item, account)
        }
        
      }
    }
  }


  def fetchOrder3: Future[Seq[(Order, Option[Seq[(OrderItem, Option[OrderItemAccount])]])]] = {
    val query = _orderTables
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_.id)
      val queryItem = (_orderItemTables joinLeft _orderItemAccountTables on (_.id===_.orderItemId)).filter(_._1.orderId.inSetBind(orderIds))
      db.run(queryItem.result)
      .map { items => 
        items.groupBy(_._1.orderId)
      }
      .map {
        orderItemsMap =>
        orders.map(o => (o, orderItemsMap.get(o.id)))
      }
    }
  }

  def fetchOrder4: Future[Seq[(Order, Option[Seq[(OrderItem, Seq[Option[OrderItemAccount]])]])]] = {
  //def fetchOrder4: Future[Seq[Order]] = {
    val query = _orderTables
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_.id)
      val queryItem = (_orderItemTables joinLeft _orderItemAccountTables on (_.id === _.orderItemId)).filter(_._1.orderId.inSetBind(orderIds))
      db.run(queryItem.result)
      .map ( items => 
        items.groupBy(_._1.id) map { case (_, items) =>
          val orderItem = items.head._1
          println("2222222222222222222")
          println(items.map(_._2))
          println("2222222222222222222")
          (orderItem, items.map(_._2))
        }
      )
      .map { items2 =>

        items2.toSeq.groupBy(_._1.orderId)
      }
      .map {
        orderItemsMap =>
        println("xxxxxxxxxxxxxxx")
        println(orderItemsMap)
        println("xxxxxxxxxxxxxxx")

        orders.map(o => (o, orderItemsMap.get(o.id)))
        //orders.map(o=>o)
      }
    }
  }


  def fetchOrder5: Future[Seq[(Order,Seq[(OrderItem, Seq[OrderItemAccount])])]] = {
    val query = _orderTables
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_.id)
      val queryItem = (_orderItemTables joinLeft _orderItemAccountTables on (_.id === _.orderItemId)).filter(_._1.orderId.inSetBind(orderIds))
      db.run(queryItem.result)
      .map { items => 
        items.groupBy(_._1.id) map { case (_, items) =>
          println("lllllllllllllllllllllll")
          println(items)
          println("lllllllllllllllllllllll")
          val orderItem = items.head._1
          (orderItem, items.map(_._2) map { item => item match {
              case Some(o: OrderItemAccount) => o
            }
          })
        }
      }
      .map { items2 =>
        println("222222222222222222")
        println(items2)
        println("222222222222222222")
        println("333333333333333333333")
        println(items2.toSeq)
        println("333333333333333333333")
        items2.toSeq.groupBy(_._1.orderId)
      }
      .map {
        orderItemsMap =>
        orders.map(o => (o, orderItemsMap.getOrElse(o.id, Seq.empty[(OrderItem, Seq[OrderItemAccount])])))
      }
    }
  }


  def fetchOrder6: Future[Seq[( (Order, OrderRelate1),Seq[(OrderItem, Seq[OrderItemAccount])])]] = {
    val query = _orderTables join _orderRelate1Tables on (_.id === _.orderId)
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_._1.id)
      val queryItem = (_orderItemTables joinLeft _orderItemAccountTables on (_.id === _.orderItemId)).filter(_._1.orderId.inSetBind(orderIds))
      db.run(queryItem.result)
      .map { items => 
        items.groupBy(_._1.id) map { case (_, items) =>
        
          val orderItem = items.head._1
          (orderItem, items.map(_._2) map { item => item match {
              case Some(o: OrderItemAccount) => o
            }
          })
        }
      }
      .map { items2 =>
      
        items2.toSeq.groupBy(_._1.orderId)
      }
      .map {
        orderItemsMap =>
        orders.map(o => (o, orderItemsMap.getOrElse(o._1.id, Seq.empty[(OrderItem, Seq[OrderItemAccount])])))
      }
    }
  }


  def fetchOrder7: Future[Seq[( Order, OrderRelate1, OrderRelate2,Seq[(OrderItem, Seq[OrderItemAccount])])]] = {
    val query = _orderTables  join _orderRelate1Tables on (_.id === _.orderId) join 
    _orderRelate2Tables on (_._2.id === _.orderRelateId)
    println(query.result.statements.head)
    db.run(query.result).flatMap { orders => 
      val orderIds = orders.map(_._1._1.id)
      val queryItem = (_orderItemTables joinLeft _orderItemAccountTables on (_.id === _.orderItemId)).filter(_._1.orderId.inSetBind(orderIds))
      db.run(queryItem.result)
      .map { items => 
        items.groupBy(_._1.id) map { case (_, items) =>
        
          val orderItem = items.head._1
          (orderItem, items.map(_._2) map { item => item match {
              case Some(o: OrderItemAccount) => o
            }
          })
        }
      }
      .map { items2 =>
        println("xxxxxxxxxxxxxxxxxxxx")
        println(items2)
        println("xxxxxxxxxxxxxxxxxxxx")

        items2.toSeq.groupBy(_._1.orderId)
      }
      .map {
        orderItemsMap =>
        orders.map(o => (o._1._1, o._1._2, o._2, orderItemsMap.getOrElse(o._1._1.id, Seq.empty[(OrderItem, Seq[OrderItemAccount])])))
      }
    }
  }



  def fetchOrder8(name: Option[String], id: Option[String]): Future[Seq[Order]] = {
    var query = _orderTables
    var query1 = name match {
      case Some(v) => query.filter(t=>t.name like s"%$v%")
      case None => query
    }
    query1 = id match {
      case Some(v) => query1.filter(_.id> v.toInt)
      case None => query1
    }
    println(query1.result.statements.head)
    db.run(query1.result)
    
  }

  def saveOrder(name: String): Future[Int] = {
    db.run((_orderTables returning _orderTables.map(_.id)) += Order(0, name))
  }

  def saveOrder2(name: String): Future[Order] = {
    db.run(((_orderTables returning _orderTables.map(_.id)) += Order(0, name)).map { id =>
      Order(id, name)
    })  
  }
  def saveOrder3(names: Seq[String]): Future[Seq[Order]] = {
    db.run(DBIO.sequence(names.map { name => {
        ((_orderTables returning _orderTables.map(_.id)) += Order(0, name)).flatMap { id =>
          DBIO.successful(Order(id, name))
        }
      }      
    }).transactionally)  
  }

  def saveOrder4(orders: Seq[Order]) : Future[Seq[Order]] = {
    db.run(DBIO.sequence(orders.map { order =>
      order.id match {
        case 0 => {
           ((_orderTables returning _orderTables.map(_.id)) += order).flatMap { id =>
             DBIO.successful(order.copy(id=id))
            }
        }
        case _ => {
          _orderTables.filter(t=>t.id===order.id).update(order).flatMap { _ => 
            DBIO.successful(order)
          }
        }
      }
    }).transactionally)
  }



  
   

  
}