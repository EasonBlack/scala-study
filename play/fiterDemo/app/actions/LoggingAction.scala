package actions

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.Logging

class LoggingAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) {
  
    override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
      println("Logging Action INVOLVE")
      block(request).map { r =>
        println(r)
        r
      }
    }
}

