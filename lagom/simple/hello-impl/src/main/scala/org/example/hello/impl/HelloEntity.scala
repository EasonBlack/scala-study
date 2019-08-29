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

  override def initialState: HelloState = HelloState("Yes", LocalDateTime.now.toString)


  override def behavior: Behavior = {
    case HelloState(message, _) => Actions().onReadOnlyCommand[Hello, String] {
      case (Hello(id), ctx, state) =>
        ctx.reply(s"$message ......, $id!!!!")
    }.onReadOnlyCommand[Hello3, String] {
      case (Hello3(id,name), ctx, state) =>
        ctx.reply(s"$message, $id and $name!!!!")
    }
  }
}




case class HelloState(message: String, timestamp: String)

object HelloState {
  implicit val format: Format[HelloState] = Json.format
}


sealed trait HelloEvent extends AggregateEvent[HelloEvent] {
  def aggregateTag = HelloEvent.Tag
}
object HelloEvent {
  val Tag = AggregateEventTag[HelloEvent]
}

sealed trait HelloCommand[R] extends ReplyType[R]

case class Hello(name: String) extends HelloCommand[String]

object Hello {
  implicit val format: Format[Hello] = Json.format
}
case class Hello3(id: String, name: String) extends HelloCommand[String]

object Hello3 {
  implicit val format: Format[Hello3] = Json.format
}


object HelloSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[Hello],
    JsonSerializer[Hello3],
    JsonSerializer[HelloState]
  )
}
