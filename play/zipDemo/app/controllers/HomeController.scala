package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import java.io.{BufferedInputStream, File, FileInputStream, FileOutputStream}
import java.util.zip.{ZipEntry, ZipOutputStream}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit exec: ExecutionContext) extends AbstractController(cc) {


  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getZip() = Action {
    val zipFile = s"/Users/eason/Downloads/test.zip"
    val zip = new ZipOutputStream(new FileOutputStream(zipFile))
    try {
      Seq("1", "2", "3").foreach { case  name =>
       
        zip.putNextEntry(new ZipEntry(s"$name.jpg"))
        val in = new BufferedInputStream(new FileInputStream(s"/Users/eason/Downloads/a/$name.jpg"))         
        try {
          var b = in.read()
          while (b > -1) {
            zip.write(b)
            b = in.read()
          }
        } finally {
          in.close()
          zip.closeEntry()
        }
      }
    } finally {
      zip.close()
    }
    Ok.sendFile(new File(zipFile))
  }

  def postGetZip() = Action.async {
    val zipFile = s"/Users/eason/Downloads/test.zip"
    val zip = new ZipOutputStream(new FileOutputStream(zipFile))
    try {
      Seq("1", "2", "3").foreach { case  name =>
       
        zip.putNextEntry(new ZipEntry(s"$name.jpg"))
        val in = new BufferedInputStream(new FileInputStream(s"/Users/eason/Downloads/a/$name.jpg"))         
        try {
          var b = in.read()
          while (b > -1) {
            zip.write(b)
            b = in.read()
          }
        } finally {
          in.close()
          zip.closeEntry()
        }
      }
    } finally {
      zip.close()
    }
    Future.successful(Ok.sendFile(new File(zipFile)))
  }
}
