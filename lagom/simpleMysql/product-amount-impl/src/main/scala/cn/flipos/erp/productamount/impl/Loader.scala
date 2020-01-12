package cn.flipos.erp.productamount.impl

import cn.flipos.erp.productamount.api.ProductAmountService
import cn.flipos.erp.productamount.impl.dao.ProductDao
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.api.{Descriptor, ServiceLocator}
import com.lightbend.lagom.scaladsl.persistence.jdbc.JdbcPersistenceComponents
import com.lightbend.lagom.scaladsl.persistence.slick.SlickPersistenceComponents
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire.wire
import play.api.db.HikariCPComponents
import play.api.libs.ws.ahc.AhcWSComponents

import scala.collection.immutable

class ProductAmountApplicationLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication = new ProductAmountApplication(context) {
    override def serviceLocator: ServiceLocator = NoServiceLocator
  }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication = new ProductAmountApplication(context) {
    override def serviceLocator: ServiceLocator = NoServiceLocator
  }

  override def describeService: Option[Descriptor] = Some(readDescriptor[ProductAmountService])
}

abstract class ProductAmountApplication(context: LagomApplicationContext) extends LagomApplication(context)
  with AhcWSComponents with JdbcPersistenceComponents with HikariCPComponents with SlickPersistenceComponents {

  private[this] val dao = wire[ProductDao]

  private[this] val readSideProcessor = wire[ProductAmountReadSideProcessor]

  persistentEntityRegistry.register(wire[ProductAmountEntity])

  override def lagomServer: LagomServer = serverFor[ProductAmountService](wire[ProductAmountServiceImpl])

  override def jsonSerializerRegistry: JsonSerializerRegistry = new JsonSerializerRegistry {
    override def serializers: immutable.Seq[JsonSerializer[_]] = List(
      JsonSerializer[InitProduct],
      JsonSerializer[SetProduct],
      JsonSerializer[UnsetProduct],
      JsonSerializer[ConsumeProduct],
      JsonSerializer[ProductInit],
      JsonSerializer[ProductCreated],
      JsonSerializer[ProductConsumed],
      JsonSerializer[ProductUnset],
      JsonSerializer[ProductUninitialized.type],
      JsonSerializer[ProductActive]
    )
  }
}
