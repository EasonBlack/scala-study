package cn.flipos.erp.productamount.impl

import java.util.UUID

import cn.flipos.api.common.JsonUtils
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger, PersistentEntity}
import play.api.libs.json.{Format, Json}

import scala.math.BigDecimal.RoundingMode

/**
  * Represents a product in a branch. The key is `b{branchId}p{productId}`.
  *
  * ==State==
  * - `ProductUninitialized`
  * - `ProductActive`
  *
  * ==Behavior==
  * When it is `ProductUninitialized`:
  * - Initialize: `InitProduct` -> `ProductInit` -> `ProductActive`
  * - Set and activate: `SetProduct` -> `ProductCreated` -> `ProductActive`
  * - Ignore consumption: `ConsumeProduct` -> return `None`.
  *
  * When it is `ProductActive`:
  * - Set a new amount: `SetProduct` -> `ProductUpdated` -> `ProductActive`
  * - Consume: `ConsumeProduct` -> `ProductConsumed` -> `ProductActive`
  * - Re-initialize: `InitProduct` -> `ProductInit` -> `ProductActive`
  * - Unset to uninitialized: `UnsetProduct` -> `ProductUnset` -> `ProductUninitialized`
  *
  * @author Raito
  */
class ProductAmountEntity extends PersistentEntity {
  override type Command = ProductCommand
  override type Event = ProductEvent
  override type State = ProductState

  override def initialState: ProductState = ProductUninitialized

  override def behavior: Behavior = {
    case ProductUninitialized => Actions().onCommand[InitProduct, Option[Double]] {
      case (cmd: InitProduct, ctx, _) => ctx.thenPersist(ProductInit(cmd.amount))(_ => ctx.reply(Some(cmd.amount)))
    }.onCommand[SetProduct, Option[Double]] {
      case (cmd: SetProduct, ctx, _) =>
        ctx.thenPersist(ProductCreated(cmd.branchId, cmd.productId, cmd.amount, UUID.randomUUID.toString))(_ => ctx.reply(Some(cmd.amount)))
    }.onReadOnlyCommand[ConsumeProduct, Option[Double]] {
      case (_: ConsumeProduct, ctx, _) => ctx.reply(None)
    }.onReadOnlyCommand[UnsetProduct, Option[Double]] {
      case (_: UnsetProduct, ctx, _) => ctx.reply(None)
    }.onEvent {
      case (e: ProductInit, _) => ProductActive(e.amount)
    }.onEvent {
      case (e: ProductCreated, _) => ProductActive(e.amount)
    }

    case ProductActive(amountBefore) => Actions().onCommand[SetProduct, Option[Double]] {
      case (cmd: SetProduct, ctx, _) =>
        ctx.thenPersist(ProductUpdated(cmd.branchId, cmd.productId, cmd.amount, UUID.randomUUID.toString))(_ => ctx.reply(Some(cmd.amount)))
    }.onCommand[ConsumeProduct, Option[Double]] {
      case (cmd: ConsumeProduct, ctx, _) =>
        val amountAfter = formatDouble(amountBefore - cmd.amount)
        ctx.thenPersist(ProductConsumed(cmd.branchId, cmd.productId, cmd.amount, amountAfter, cmd.code))(_ => ctx.reply(Some(amountAfter)))
    }.onCommand[UnsetProduct, Option[Double]] {
      case (cmd: UnsetProduct, ctx, _) => ctx.thenPersist(ProductUnset(cmd.branchId, cmd.productId))(_ => ctx.reply(None))
    }.onCommand[InitProduct, Option[Double]] {
      case (cmd: InitProduct, ctx, _) => ctx.thenPersist(ProductInit(cmd.amount))(_ => ctx.reply(Some(cmd.amount)))
    }.onEvent {
      case (e: ProductInit, _) => ProductActive(e.amount)
    }.onEvent {
      case (e: ProductUpdated, _) => ProductActive(e.amountAfter)
    }.onEvent {
      case (e: ProductConsumed, _) => ProductActive(e.amountAfter)
    }.onEvent {
      case (_: ProductUnset, _) => ProductUninitialized
    }
  }

  private def formatDouble(value: Double) = BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toDouble
}

trait ProductCommand extends ReplyType[Option[Double]]

object InitProduct {
  implicit val format: Format[InitProduct] = Json.format
}

/**
  * Issued to initialize the product.
  */
case class InitProduct(branchId: Long, productId: Long, amount: Double) extends ProductCommand

object SetProduct {
  implicit val format: Format[SetProduct] = Json.format
}

/**
  * Issued to set a new amount.
  */
case class SetProduct(branchId: Long, productId: Long, amount: Double) extends ProductCommand

object ConsumeProduct {
  implicit val format: Format[ConsumeProduct] = Json.format
}

/**
  * Issued to consume some amount of the product.
  */
case class ConsumeProduct(branchId: Long, productId: Long, amount: Double, code: String) extends ProductCommand

object UnsetProduct {
  implicit val format: Format[UnsetProduct] = Json.format
}

/**
  * Issued to unset the product ot uninitialized state.
  */
case class UnsetProduct(branchId: Long, productId: Long) extends ProductCommand

object ProductEvent {
  val TAG = AggregateEventTag.sharded[ProductEvent](4)
}

trait ProductEvent extends AggregateEvent[ProductEvent] {
  override def aggregateTag: AggregateEventTagger[ProductEvent] = ProductEvent.TAG
}

object ProductInit {
  implicit val format: Format[ProductInit] = Json.format[ProductInit]
}

/**
  * Emitted when the product is initialized.
  */
case class ProductInit(amount: Double) extends ProductEvent

object ProductCreated {
  implicit val format: Format[ProductCreated] = Json.format
}

/**
  * Emitted when the product is activated by setting a new amount.
  */
case class ProductCreated(branchId: Long, productId: Long, amount: Double, code: String) extends ProductEvent

object ProductUpdated {
  implicit val format: Format[ProductUpdated] = Json.format
}

/**
  * Emitted when a new active amount is set.
  */
case class ProductUpdated(branchId: Long, productId: Long, amountAfter: Double, code: String) extends ProductEvent

object ProductConsumed {
  implicit val format: Format[ProductConsumed] = Json.format
}

/**
  * Emitted when some amount of the product is consumed.
  */
case class ProductConsumed(branchId: Long, productId: Long, amount: Double, amountAfter: Double, code: String) extends ProductEvent

object ProductUnset {
  implicit val format: Format[ProductUnset] = Json.format
}

/**
  * Emitted when the product is unset to uninitialized state.
  */
case class ProductUnset(branchId: Long, productId: Long) extends ProductEvent

trait ProductState

case object ProductUninitialized extends ProductState {
  implicit val format: Format[ProductUninitialized.type] = JsonUtils.singletonFormat(ProductUninitialized)
}

object ProductActive {
  implicit val format: Format[ProductActive] = Json.format
}

case class ProductActive(amount: Double) extends ProductState


