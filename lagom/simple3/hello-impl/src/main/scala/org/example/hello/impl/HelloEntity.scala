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
    .onCommand[AddToHello, Done] {
      case (AddToHello(name), context, state) =>
        context.thenPersist(
          AddedToHelloEvent(name)
        ) { _ =>
          context.reply(Done)
        }
      }
    .onEvent {
      case (AddedToHelloEvent(name), state) => {
        println(state.products)
        HelloState(name :: state.products)
      }
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


sealed trait HelloCommand[R] extends ReplyType[R]

case class Hello(name: String) extends HelloCommand[String]
case class AddToHello(name: String) extends HelloCommand[Done]


object Hello {
  implicit val format: Format[Hello] = Json.format
}

case class GetHello() extends HelloCommand[List[String]]


object HelloSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[Hello],
    JsonSerializer[HelloState]
  )
}
