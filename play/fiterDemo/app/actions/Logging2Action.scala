package actions

import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import play.api.Logging


case class Logging[A](action: Action[A]) extends Action[A] with play.api.Logging {
  def apply(request: Request[A]): Future[Result] = {
    logger.info("Calling action")
    println("ACTION INVOICE 1")
    action(request)
  }

  override def parser           = action.parser
  override def executionContext = action.executionContext
}


class Logging2Action @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    println("ACTION INVOICE 2")
    block(request)
  }
  override def composeAction[A](action: Action[A]) = new Logging(action)
}