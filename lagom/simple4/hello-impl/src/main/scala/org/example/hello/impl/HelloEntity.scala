package org.example.hello.impl

import java.time.LocalDateTime

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import play.api.libs.json.{Format, Json}

import scala.collection.immutable.Seq


class HelloEntity extends PersistentEntity {

  override type Command = HelloCommand[_]
  override type Event = HelloEvent
  override type State = HelloState

  override def initialState: HelloState = HelloState(List.empty)


  override def behavior: Behavior = {
    case HelloState(_) => Actions()
    .onReadOnlyCommand[GetHello, List[String]] {
      case (GetHello(), ctx, state) =>
        ctx.reply(state.products)
    }
    .onReadOnlyCommand[GetHello2, String] {
      case (GetHello2(), ctx, state) =>
        ctx.reply("xxxxxxxxxxx")
    }
    .onReadOnlyCommand[GetHello3, String] {
      case (GetHello3(), ctx, state) =>
        ctx.reply("yyyyyyyyyyy")
    }
    .onCommand[AddToHello, Done] {
      case (AddToHello(name), context, state) =>
        context.thenPersist(
          AddedToHelloEvent(name)
        ) { _ =>
          context.reply(Done)
      }

      
      }
    .onCommand[RemoveFromCartCommand, Done] {
      case (RemoveFromCartCommand(product), context, state) =>
        context.thenPersist(
          RemovedFromCartEvent(product)
        ) { _ =>
          context.reply(Done)
        }
    }
    .onCommand[AddToCartCommand, Done] {
      case (AddToCartCommand(product), context, state) =>
        context.thenPersist(
          AddedToCartEvent(product)
        ) { _ =>
          context.reply(Done)
        }
    }
    .onEvent {
      case (AddedToHelloEvent(name), state) => {
        HelloState(name :: state.products)
      }
      case (AddedToCartEvent(product), state) =>
        HelloState(product :: state.products)
      case (RemovedFromCartEvent(product), state) =>
        HelloState(state.products.filterNot(_ == product))
    }
  }
}


case class HelloState(products: List[String])
object HelloState {
  implicit val format: Format[HelloState] = Json.format
}


sealed trait HelloEvent extends AggregateEvent[HelloEvent] {
  def aggregateTag = HelloEvent.Tag
}
object HelloEvent {
  val Tag = AggregateEventTag[HelloEvent]
}

case class AddedToHelloEvent(name: String) extends HelloEvent
case class AddedToCartEvent(name: String) extends HelloEvent
case class RemovedFromCartEvent(name: String) extends HelloEvent


sealed trait HelloCommand[R] extends ReplyType[R]

case class Hello(name: String) extends HelloCommand[String]
case class AddToHello(name: String) extends HelloCommand[Done]


object Hello {
  implicit val format: Format[Hello] = Json.format
}

case class GetHello() extends HelloCommand[List[String]]
case class GetHello2() extends HelloCommand[String]
case class GetHello3() extends HelloCommand[String]
case class AddToCartCommand(product: String) extends HelloCommand[Done]
case class RemoveFromCartCommand(product: String) extends HelloCommand[Done]


object HelloSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[Hello],
    JsonSerializer[HelloState]
  )
}
