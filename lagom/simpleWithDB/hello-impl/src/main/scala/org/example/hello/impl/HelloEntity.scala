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

  override def initialState: HelloState = HelloState.empty

  override def behavior: Behavior = {
    case HelloUnInitialized(_) => Actions()
    .onReadOnlyCommand[GetHello, List[String]] {
      case (GetHello(), ctx, state) =>
        ctx.reply(state)
    }
    .onCommand[AddToHello, Done] {
      case (HelloPostEvent(id, name), context, state) =>
        context.thenPersist(
          HelloState(id, name)
        ) { _ =>
          context.reply(Done)
      }   
    }
  }

}


case class HelloState(id: Long, name: String)
object HelloState {
  implicit val format: Format[HelloState] = Json.format
}


sealed trait HelloEvent extends AggregateEvent[HelloEvent] {
  def aggregateTag = HelloEvent.Tag
}
object HelloEvent {
  val Tag = AggregateEventTag[HelloEvent]
}

case class HelloPostEvent(id: Long, name: String) extends HelloEvent


sealed trait HelloCommand[R] extends ReplyType[R]



object HelloSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[HelloState]
  )
}
