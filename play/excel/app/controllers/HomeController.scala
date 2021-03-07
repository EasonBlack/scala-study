package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.io._

import org.apache.poi.ss.usermodel.{Row, WorkbookFactory, Workbook }
import java.nio.file.{Paths};
import java.io.{File, FileOutputStream, PrintWriter, StringWriter, FileInputStream, InputStream}

import com.opencsv.CSVReader
import scala.io.{Codec, Source}

import scala.concurrent.{ExecutionContext, Future}
import services.ExcelExporter
import org.apache.poi.EmptyFileException;
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import scala.collection.{JavaConverters, mutable}
// import scala.util.Using

@Singleton
class HomeController @Inject()(cc: ControllerComponents, excelExporter: ExcelExporter)(implicit exec: ExecutionContext) extends AbstractController(cc) {

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

  def fetch7 = Action {
  
    val input = new FileInputStream("F:/temp/a.xlsx")
    try {
      
      val wb = WorkbookFactory.create(input)
      val sheet = wb.getSheetAt(0)
      var i = 2
      Seq("a", "b", "c", "d").foreach { v =>
        val row = sheet.createRow(i)
        var j = 0
        row.createCell(0).setCellValue(v)
        i = i + 1
      }
       val output = new FileOutputStream("F:/temp/a.xlsx")
      try {
        wb.write(output)
        Ok("aaaaaaa")
      } finally {
        output.close()
      }

    } finally {
      input.close()     
    }
  }



  def fetch8 = Action {  request => 
      var i = 2
      for ( _ <- 1 to 20) {
       
        // tryWith( new FileInputStream("F:/temp/a.xlsx")) { input => 
        //     val wb = WorkbookFactory.create(input)
        //     val sheet = wb.getSheetAt(0)
        //     (1 to 40000).foreach { v =>
        //       val row = sheet.createRow(i)
        //       row.createCell(0).setCellValue(v)
        //       i = i + 1
        //     }
        //     tryWith(new FileOutputStream("F:/temp/a.xlsx")) { output => 
        //       wb.write(output)
        //       wb.close();
        //     }
        // }

        // val input = new FileInputStream("F:/temp/a.xlsx")
        val input = new File("F:/temp/a.xlsx")
        val wb = WorkbookFactory.create(input)
        val output = new FileOutputStream("F:/temp/a1.xlsx")
        try {
          // val input = new FileInputStream("F:/temp/a.xlsx")
          // val wb = WorkbookFactory.create(input)
          val sheet = wb.getSheetAt(0)
          (1 to 40000).foreach { v =>
            val row = sheet.createRow(i)
            row.createCell(0).setCellValue(v)
            i = i + 1
          }
          //input.close()
          wb.write(output)
          println(s"$i finished xxxxxxxxxxxxxxxxxx")
        } finally {
          output.flush()
          output.close()
          wb.close()
          // println("xxxxxxxxxxxxxxxxxxxxxxxxx")
          // println(input)
          // println("xxxxxxxxxxxxxxxxxxxxxxxxx")

          // input.close()
          // println("xxxxxxxxxxxxxxxxxxxxxxxxx")
          // println(input)
          // println("xxxxxxxxxxxxxxxxxxxxxxxxx")
          // input.close()
         
         
        }
        
      }
      Ok("cccccc")
      
      

   
  }


  // 过滤掉报错
  def fetch9 = Action {  request => 
      var i = 2
      var num = 0;
      var wb: Option[Workbook]  =  None
      var input: Option[FileInputStream] = None   
      try {
        for ( _ <- 1 to 20) {

          input = Some(new FileInputStream("F:/temp/a.xlsx"))
          try {
            println(input)
            wb = Some(WorkbookFactory.create(input.get))
          } catch {
            case e: EmptyFileException => { println("pretend nothing happened")} 
            case _  => { println("other happend") }
          }
         
          val output = new FileOutputStream("F:/temp/a.xlsx")
          val time1=System.currentTimeMillis()
          val sheet = wb.get.getSheetAt(0)
          (1 to 40000).foreach { v =>
            val row = sheet.createRow(i)
            row.createCell(0).setCellValue(v)
            i = i + 1
          }
          wb.get.write(output)
          num = num + 1
          println(s"$num start")
         
          output.flush()
          output.close()
          wb.get.close()
          val time2=System.currentTimeMillis()
          println(time1 - time2)
          println(s"$num finished")
          
          
         
        }
      } catch {
        case a:EmptyFileException  => {
          println("STH WRONG")
        } 
        case b => {
          println(b)
        }
      } finally {
      
      }
      
      println("all finished")
      Ok("ddddddd")
      
      

   
  }



  // 利用sheet
  def fetch10 = Action {  request => 
      var i = 2
      var num = 0;
      try {
        for ( _ <- 1 to 20) {
      
          val input = new FileInputStream("F:/temp/a.xlsx")
          val wb = WorkbookFactory.create(input)
          val output = new FileOutputStream("F:/temp/a.xlsx")
          try {
              
            val time1=System.currentTimeMillis()
            wb.createSheet(num.toString)
            val sheet = wb.getSheetAt(num + 1)
            (1 to 40000).zipWithIndex.foreach { case (v, index) =>
              val row = sheet.createRow(index)
              row.createCell(0).setCellValue(v)
              // i = i + 1
            }
            wb.write(output)
            num = num + 1
            println(s"$num start")
            input.close()
            output.flush()
            output.close()
            wb.close()
            val time2=System.currentTimeMillis()
            println(time1 - time2)
            // Thread.sleep(3000L);
          }
          println(s"$num finished")
        }
      } catch {
        case a:EmptyFileException  => {
          println("STH WRONG")
        } 
        case b => {
          println(b)
        }
      } finally {
      
      }
      
      println("all finished")
      Ok("eeeeeee")
      
      

   
  }

  // SXSSFWorkbook 保存
  def fetch11 = Action {  request => 
      var i = 2
      var num = 0;
      
      try {
        val wb = new SXSSFWorkbook(100)
        wb.createSheet()
        val sheet = wb.getSheetAt(0)
        val output = new FileOutputStream("F:/temp/a.xlsx")
        for ( _ <- 1 to 20) {
    
          try {
              
            val time1=System.currentTimeMillis()
          
            (1 to 40000).foreach { v =>
              val row = sheet.createRow(i)
              row.createCell(0).setCellValue(v)
              i = i + 1
            }
           
            num = num + 1
            println(s"$num start")
            
           
            val time2=System.currentTimeMillis()
            println(time1 - time2)
            // Thread.sleep(3000L);
          }
         
          println(s"$num finished")
        }
        wb.write(output)
        output.flush()
        output.close()
        wb.dispose()
      } catch {
        case a:EmptyFileException  => {
          println("STH WRONG")
        } 
        case b => {
          println(b)
        }
      } finally {
      
      }
      
      println("all finished")
      Ok("ddddddd")
      
      

   
  }


  //  def fetch12 = Action {  request => 
  //     var i = 2
  //     var num = 0;
  //     var wb: Option[Workbook]  =  None
  //     var input: Option[FileInputStream] = None   
  //     try {
  //       for ( _ <- 1 to 20) {

  //         input = Some(new FileInputStream("F:/temp/a.xlsx"))
  //         try {
  //           println(input)
  //           wb = Some(WorkbookFactory.create(input.get))
  //         } catch {
  //           case e: EmptyFileException => { println("pretend nothing happened")} 
  //           case _  => { println("other happend") }
  //         }
         
  //         val output = new FileOutputStream("F:/temp/a.xlsx")
  //         val time1=System.currentTimeMillis()
  //         val sheet = wb.get.getSheetAt(0)
  //         (1 to 40000).foreach { v =>
  //           val row = sheet.createRow(i)
  //           row.createCell(0).setCellValue(v)
  //           i = i + 1
  //         }
  //         wb.get.write(output)
  //         num = num + 1
  //         println(s"$num start")
         
  //         output.flush()
  //         output.close()
  //         wb.get.close()
  //         val time2=System.currentTimeMillis()
  //         println(time1 - time2)
  //         println(s"$num finished")
          
          
         
  //       }
  //     } catch {
  //       case a:EmptyFileException  => {
  //         println("STH WRONG")
  //       } 
  //       case b => {
  //         println(b)
  //       }
  //     } finally {
      
  //     }
      
  //     println("all finished")
  //     Ok("ddddddd")
      
      

   
  // }



  private def tryWith[A <: Closeable](resource: A)(func: A => Unit) = {
    try {
      func(resource)
    } finally {
      if(true) {
        //resource.close()
        try {
            resource.close();
        } catch   {
         case e => {
            println("xxxxxxxxxxxxxxxxxxxxxx")
            println(e)
            println("xxxxxxxxxxxxxxxxxxxxxx")
         }
        }
      }
    
      //  org.apache.commons.io.IOUtils.closeQuietly(resource)
    }
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
