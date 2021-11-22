package filters

import javax.inject.Inject
import akka.stream.Materializer
import play.api.Logging
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future

class LoggingFilter @Inject() (implicit val mat: Materializer, ec: ExecutionContext) extends Filter with Logging {
  def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    val startTime = System.currentTimeMillis

    nextFilter(requestHeader).map { result =>
      val endTime     = System.currentTimeMillis
      val requestTime = endTime - startTime

      logger.info(
        s"${requestHeader.method} ${requestHeader.uri} took ${requestTime}ms and returned ${result.header.status}"
      )
     
      println(s"""XXXX ${requestHeader.method} ${requestHeader.uri} 
      ${requestHeader.path}
      ${requestHeader.queryString}
      took ${requestTime}ms and returned ${result.header.status}""")
      println(result.toString)
      println(result.body)
      println(requestHeader.queryString.get("aa"))
      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }
}