package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.io._

import org.apache.poi.ss.usermodel.{Row, WorkbookFactory, Workbook }
import java.nio.file.{Paths, Files};
import java.io.{File, FileOutputStream, PrintWriter, StringWriter, FileInputStream, InputStream}
import play.api.http.HttpEntity
import com.opencsv.CSVReader
import scala.io.{Codec, Source}
import akka.util.ByteString

import scala.concurrent.{ExecutionContext, Future}
import services.ExcelExporter
import org.apache.poi.EmptyFileException;
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import scala.collection.{JavaConverters, mutable}
// import scala.util.Using

@Singleton
class MacController @Inject()(cc: ControllerComponents, excelExporter: ExcelExporter)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def indexFile() = Action {
     Ok(views.html.indexFile2())
  }

  def export = Action {implicit  request => 
    var headers = Seq("a", "b", "c")
    var rows = Seq(Seq("a1", "b1", "c1"), Seq("a2", "b2", "c2"), Seq("a3", "b3", "c3"))
   
    val wb = new SXSSFWorkbook(1)
		val output = new FileOutputStream("/Users/eason/temp/temp.xlsx")
    try {
      wb.createSheet()
      var sheet = wb.getSheetAt(0)
      val titleRow = sheet.createRow(0)
      titleRow.createCell(0).setCellValue(s"aaaaaa")
      val headRow = sheet.createRow(2)
      headers.zipWithIndex.foreach { case (h, index) => 
        headRow.createCell(index).setCellValue(h)
      }

      var i = 3
      rows.zipWithIndex.foreach { case (item, index) => 
        val row = sheet.createRow(i)
        item.zipWithIndex.foreach { case (c, cindex) =>
          row.createCell(cindex).setCellValue(c)
        }
        i = i + 1         
      }
      wb.write(output)
      Ok("OK")
    }	finally {
      output.flush()
      output.close()
      wb.close()
      wb.dispose()
    }
    	
  
  }

  def export2 = Action {implicit  request => 
    var headers = Seq("a", "b", "c")
    var rows = Seq(Seq("a1", "b1", "c1"), Seq("a2", "b2", "c2"), Seq("a3", "b3", "c3"))
   
    val wb = new SXSSFWorkbook(1)
		val output = new FileOutputStream("/Users/eason/temp/temp.xlsx")
    try {
      wb.createSheet()
      var sheet = wb.getSheetAt(0)
      val titleRow = sheet.createRow(0)
      titleRow.createCell(0).setCellValue(s"aaaaaa")
      val headRow = sheet.createRow(2)
      headers.zipWithIndex.foreach { case (h, index) => 
        headRow.createCell(index).setCellValue(h)
      }
      var i = 3
      rows.zipWithIndex.foreach { case (item, index) => 
        val row = sheet.createRow(i)
        item.zipWithIndex.foreach { case (c, cindex) =>
          row.createCell(cindex).setCellValue(c)
        }
        i = i + 1         
      }
      wb.write(output)
      val byteArray = Files.readAllBytes(Paths.get("/Users/eason/temp/temp.xlsx"))
      Result(ResponseHeader(200, Map("Content-Disposition" -> s"attachment;filename=aaa.xlsx")),
        HttpEntity.Strict(ByteString(byteArray), Some("application/vnd.ms-excel;charset=UTF-8")))

    }	finally {
      output.flush()
      output.close()
      wb.close()
      wb.dispose()
    }
    	
  
  }


  def export3 = Action {implicit  request => 
    var headers = Seq("a", "b", "c")
    var rows = Seq(Seq("aa1", "b1", "c1"), Seq("aa2", "b2", "c2"), Seq("aa3", "b3", "c3"))
   
    val wb = new SXSSFWorkbook(1)
		val outputFile: java.io.File = File.createTempFile("temp", ".xlsx")
    val output = new FileOutputStream(outputFile)
    println(outputFile.toPath)
    try {
      wb.createSheet()
      var sheet = wb.getSheetAt(0)
      val titleRow = sheet.createRow(0)
      titleRow.createCell(0).setCellValue(s"aaaaaa")
      val headRow = sheet.createRow(2)
      headers.zipWithIndex.foreach { case (h, index) => 
        headRow.createCell(index).setCellValue(h)
      }
      var i = 3
      rows.zipWithIndex.foreach { case (item, index) => 
        val row = sheet.createRow(i)
        item.zipWithIndex.foreach { case (c, cindex) =>
          row.createCell(cindex).setCellValue(c)
        }
        i = i + 1         
      }
      wb.write(output)
      val byteArray = Files.readAllBytes(outputFile.toPath)
      Result(ResponseHeader(200, Map("Content-Disposition" -> s"attachment;filename=aaa.xlsx")),
        HttpEntity.Strict(ByteString(byteArray), Some("application/vnd.ms-excel;charset=UTF-8")))
        
    }	finally {
      output.flush()
      output.close()
      outputFile.deleteOnExit()
      wb.close()
      wb.dispose()
    }
    	
  
  }

}