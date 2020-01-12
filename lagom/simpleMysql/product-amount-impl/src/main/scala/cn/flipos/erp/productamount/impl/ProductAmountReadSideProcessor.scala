package cn.flipos.erp.productamount.impl

import cn.flipos.erp.productamount.impl.dao.ProductDao
import com.lightbend.lagom.scaladsl.persistence.slick.SlickReadSide
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, EventStreamElement, ReadSideProcessor}
import play.api.Logger
import slick.dbio.{DBIOAction, NoStream}

import scala.reflect.ClassTag

class ProductAmountReadSideProcessor(readSide: SlickReadSide, dao: ProductDao) extends ReadSideProcessor[ProductEvent] {

  private[this] val logger = Logger(getClass.getSimpleName)

  override def buildHandler(): ReadSideProcessor.ReadSideHandler[ProductEvent] =
    readSide.builder[ProductEvent]("erpProductEvent")
      .setEventHandler[ProductInit](e => logEvent(e)(_ => DBIOAction.successful(Unit)))
      .setEventHandler[ProductCreated](e => logEvent(e)(onCreated))
      .setEventHandler[ProductUpdated](e => logEvent(e)(onUpdated))
      .setEventHandler[ProductConsumed](e => logEvent(e)(onConsumed))
      .setEventHandler[ProductUnset](e => logEvent(e)(onUnset))
      .build

  override def aggregateTags: Set[AggregateEventTag[ProductEvent]] = ProductEvent.TAG.allTags

  private def logEvent[E <: ProductEvent : ClassTag]
  (e: EventStreamElement[E])(handler: EventStreamElement[E] => DBIOAction[Any, NoStream, Nothing]) = {
    logger.debug(s"Begin to handle $e on read side.")
    handler(e)
  }

  private def onCreated(e: EventStreamElement[ProductCreated]) = {
    dao.saveOrUpdate(e.event.branchId, e.event.productId, e.event.amount, e.event.code, "CREATE")
  }

  private def onUpdated(e: EventStreamElement[ProductUpdated]) = {
    dao.saveOrUpdate(e.event.branchId, e.event.productId, e.event.amountAfter, e.event.code, "UPDATE")
  }

  private def onConsumed(e: EventStreamElement[ProductConsumed]) = {
    dao.consume(e.event.branchId, e.event.productId, e.event.amount, e.event.code)
  }

  private def onUnset(e: EventStreamElement[ProductUnset]) = {
    dao.delete(e.event.branchId, e.event.productId)
  }
}
