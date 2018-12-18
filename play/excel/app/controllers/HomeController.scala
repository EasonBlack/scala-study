package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}
import java.nio.file.{Paths};
import java.io.{File, FileOutputStream, PrintWriter, StringWriter, FileInputStream, InputStream}

import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def fetch1 = Action { request => 
    val data = java.nio.file.Files.readAllBytes(Paths.get("F:/temp/aaa.xlsx"))
    val stream = new FileOutputStream("F:/temp/dest.xlsx")
    try {
      stream.write(data)
    } finally {
      stream.close()
    }
    Ok("aaaa")
  }

  def fetch2 = Action { request => 
    var result = ""
    val workbook = WorkbookFactory.create(new File("F:/temp/aaa.xlsx"))
    val sheet = workbook.getSheetAt(0)
    sheet.forEach(row => {
      result +=  row.getCell(0)
      result +=  row.getCell(1)
    })
    Ok(result)
  }

}
