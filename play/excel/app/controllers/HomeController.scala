package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}
import java.nio.file.{Paths};
import java.io.{File, FileOutputStream, PrintWriter, StringWriter, FileInputStream, InputStream}

import com.opencsv.CSVReader
import scala.io.{Codec, Source}

import scala.concurrent.{ExecutionContext, Future}
import services.ExcelExporter

import scala.collection.{JavaConverters, mutable}

@Singleton
class HomeController @Inject()(cc: ControllerComponents, excelExporter: ExcelExporter) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def indexFile() = Action {
     Ok(views.html.indexFile())
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

  def fetch3 = Action {
    val headers = Seq("aaa", "bbb")
    val data = Seq((11,22), (3,4)).map { item =>  
        JavaConverters.seqAsJavaList(Seq(item._1, item._2))
    }
  
    excelExporter.exportData(headers, data, "dest", "统计test", Map(
        "title" -> "test", "title2"->"test2"))
  } 
  def fetch4 = Action {
    val headers = Seq("aaa", "bbb")
    val data = Seq((11,22), (3,4)).map { item =>
     
        JavaConverters.seqAsJavaList(Seq(item._1, item._2))
    }
    excelExporter.exportData2(headers, data, "dest", "统计test", Map(
        "title" -> "test", "title2"->"test2"))
  } 
  def fetch5 = Action {
    excelExporter.exportMultiple("destmultiple", "统计test-multiple", Map(
        "title" -> "test"))
  } 
  def fetch6 = Action {
    val headers = Seq("aaa", "bbb")
    val data = Seq((11,22), (3,4)).map { item =>  
        JavaConverters.seqAsJavaList(Seq(item._1, item._2))
    }
    excelExporter.exportMerge(headers, data, "destmerge", "merge", Map(
        "title" -> "test", "HH"-> "x2", "col"->"5", "col2"->"2" ))
  } 


  def upload = Action.async(parse.multipartFormData) {implicit  request => 
     request.body.file("data") match {
        case None =>  Future.successful(Ok("ERROR"))
        case Some(file) => {
            val reader = Source.fromFile(file.ref.path.toString, Codec.UTF8.name).reader
            val csvReader = new CSVReader(reader)       
            try {
              val lines = csvReader.readAll()
              for (i <- 1 until lines.size) {
                  val columns = lines.get(i)
                  val name = columns(0)
                  val code = columns(1)
                  println(name, code)
              }
              Future.successful(Ok("OK"))
            } finally {
              reader.close()
              csvReader.close()
            }
        }
     }
  }
}
