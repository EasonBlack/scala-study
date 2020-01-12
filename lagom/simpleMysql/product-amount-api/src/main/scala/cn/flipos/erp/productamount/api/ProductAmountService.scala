package test.erp.productamount.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import cn.flipos.api.common.{AccessLoggingFilter, CommonExceptionSerializer}
import com.lightbend.lagom.scaladsl.api.Service._
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}


trait ProductAmountService extends Service {


  def initProducts: ServiceCall[NotUsed, NotUsed]

  def consumeProducts: ServiceCall[Seq[ProductConsumption], NotUsed]

  def updateProducts(): ServiceCall[Seq[ProductUpdate], NotUsed]

  def findProducts(branchId: Long): ServiceCall[NotUsed, Seq[BranchProduct]]

  def subscribeConsumptions: ServiceCall[Long, Source[ProductConsumptionResult, NotUsed]]

  override def descriptor: Descriptor = {
    named("erpProductAmount").withCalls(
      restCall(Method.POST, "/init", initProducts _),
      restCall(Method.POST, "/consume", consumeProducts _),
      restCall(Method.POST, "/update", updateProducts _),
      restCall(Method.GET, "/?branchId", findProducts _),
      pathCall("/consumption/subscribe", subscribeConsumptions _)
    )
  }
}
