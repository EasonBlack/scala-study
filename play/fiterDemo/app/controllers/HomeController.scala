package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import actions.{LoggingAction, Logging2Action}


@Singleton
class HomeController @Inject()( 
  loggingAction: LoggingAction, 
  logging2Action: Logging2Action,
  // logging3Action: Logging3Action,
  cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def test() = loggingAction { 
    Ok("aaaaa123")
  }

   def test2() = logging2Action { 
    try {
      var b = 10 / 0
      Ok("bbbbbb")
    } catch {
      case _ => Ok("1")
    }
  }

  def test3() = loggingAction.andThen(logging2Action) { 
    Ok("ccccccc")
  }
  // def test4() = logging3Action("bd") { 
  //   Ok("ddddddd")
  // }

}
