package cn.flipos.erp.productamount.impl

import java.util.UUID

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import cn.flipos.api.common.JsonUtils.notUsedFormat
import cn.flipos.api.common.{LoggingService, ZookeeperServiceRegistry}
import cn.flipos.erp.productamount.api._
import cn.flipos.erp.productamount.impl.dao.ProductDao
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.{PersistentEntityRegistry, ReadSide}
import com.typesafe.config.Config
import play.api.Logger
import play.api.inject.ApplicationLifecycle

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ProductAmountServiceImpl(persistentEntityRegistry: PersistentEntityRegistry, readSide: ReadSide, dao: ProductDao,
                               readSideProcessor: ProductAmountReadSideProcessor,
                               val config: Config, val applicationLifecycle: ApplicationLifecycle)
                              (implicit val exec: ExecutionContext, materializer: Materializer)
  extends ProductAmountService with LoggingService with ZookeeperServiceRegistry {

  private[this] val errorLogger = Logger("error")

  private[this] val handlingParallelism = config.getInt("handlingParallelism")

  readSide.register[ProductEvent](readSideProcessor)

  override def consumeProducts: ServiceCall[Seq[ProductConsumption], NotUsed] = loggingCall("consumeProduct") { data =>
    Source(data.toList).runWith(Sink.foreachAsync(handlingParallelism) { item =>
      val entity = persistentEntityRegistry.refFor[ProductAmountEntity](s"b${item.branchId}p${item.productId}")
      val f = entity.ask(ConsumeProduct(item.branchId, item.productId, item.amount, item.orderNo.getOrElse(UUID.randomUUID.toString)))
      f.onComplete {
        case Success(_) => Unit
        case Failure(ex) => errorLogger.error(s"Failed to consume the product $item: $ex")
      }
      f.map(_ => Unit)
    }).map(_ => NotUsed.notUsed())
  }

  override def initProducts: ServiceCall[NotUsed, NotUsed] = loggingCall("initProducts") { _ =>
    dao.findAll.flatMap { items =>
      Source(items.toList).runWith(Sink.foreachAsync(handlingParallelism) { item =>
        val entity = persistentEntityRegistry.refFor[ProductAmountEntity](s"b${item.branchId}p${item.productId}")
        val f = entity.ask(InitProduct(item.branchId, item.productId, item.amount))
        f.onComplete {
          case Success(_) => Unit
          case Failure(ex) => errorLogger.error(s"Failed to init the product $item: $ex")
        }
        f.map(_ => Unit)
      }).map(_ => NotUsed.notUsed())
    }
  }

  override def updateProducts(): ServiceCall[Seq[ProductUpdate], NotUsed] = loggingCall("updateProducts") { data =>
    Source(data.toList).runWith(Sink.foreachAsync(handlingParallelism) { item =>
      val entity = persistentEntityRegistry.refFor[ProductAmountEntity](s"b${item.branchId}p${item.productId}")
      val cmd = item.amount match {
        case None => UnsetProduct(item.branchId, item.productId)
        case Some(v) => SetProduct(item.branchId, item.productId, v)
      }
      val f = entity.ask(cmd)
      f.onComplete {
        case Success(_) => Unit
        case Failure(ex) => errorLogger.error(s"Failed to update the product $item: $ex")
      }
      f.map(_ => Unit)
    }).map(_ => NotUsed.notUsed())
  }

  override def findProducts(branchId: Long): ServiceCall[NotUsed, Seq[BranchProduct]] = loggingCall("findProducts", branchId) { () =>
    dao.findByBranch(branchId)
  }

  override def subscribeConsumptions: ServiceCall[Long, Source[ProductConsumptionResult, NotUsed]] = ServiceCall {
    _ => Future.successful(Source.empty)
  }
}
