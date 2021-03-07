package services

import java.io.{ByteArrayOutputStream, File, FileInputStream, FileOutputStream}

import akka.util.ByteString
import com.google.inject.{Inject, Singleton}
import org.jxls.common.Context
import org.jxls.util.JxlsHelper
import play.api.http.HttpEntity
import play.api.mvc.{ResponseHeader, Result}
import org.apache.poi.ss.usermodel.{Row, WorkbookFactory}

import scala.concurrent.{ExecutionContext, Future}
import scala.collection.JavaConverters

/*
 * @see [[http://jxls.sourceforge.net JXLS Doc]]
 */
@Singleton
class ExcelExporter @Inject()() {

  def exportData(headers: Seq[String], data: Seq[Any], template: String, fileName: String,
                 model: Map[String, Object] = Map.empty): Result = {
    println(headers)
   
    val templateDir = new File("F:/temp/")
    val input = new FileInputStream(new File(templateDir, s"$template.xlsx"))
    val output = new ByteArrayOutputStream()
    try {
      val context = new Context
      model.foreach {
        case (key, value) => context.putVar(key, value)
      }
      context.putVar("headers", JavaConverters.seqAsJavaList(headers))
      context.putVar("datat", JavaConverters.seqAsJavaList(data))
      
      JxlsHelper.getInstance.processGridTemplateAtCell(input, output, context, null, "Sheet1!A1")
      val exportFileName = new String(fileName.getBytes("gbk"), "iso-8859-1")
      Result(ResponseHeader(200, Map("Content-Disposition" -> s"attachment;filename=$exportFileName.xls")),
        HttpEntity.Strict(ByteString(output.toByteArray), Some("application/vnd.ms-excel;charset=UTF-8")))
    } finally {
      output.close()
      input.close()
    }
  }
  
  def exportData2(headers: Seq[String], data: Seq[Any], template: String, fileName: String,
                 model: Map[String, Object] = Map.empty): Result = {
    println(headers)
   
    val templateDir = new File("F:/temp/")
    val input = new FileInputStream(new File(templateDir, s"$template.xlsx"))
    val output = new ByteArrayOutputStream()
    try {
      val context = new Context
      model.foreach {
        case (key, value) => context.putVar(key, value)
      }
      context.putVar("headers", JavaConverters.seqAsJavaList(headers))
      context.putVar("datat", JavaConverters.seqAsJavaList(data))
      
      JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(input, output, context);

      val exportFileName = new String(fileName.getBytes("gbk"), "iso-8859-1")
      Result(ResponseHeader(200, Map("Content-Disposition" -> s"attachment;filename=$exportFileName.xls")),
        HttpEntity.Strict(ByteString(output.toByteArray), Some("application/vnd.ms-excel;charset=UTF-8")))
    } finally {
      output.close()
      input.close()
    }
  }

  def exportMultiple(template: String, fileName: String,
                 model: Map[String, Object] = Map.empty): Result = {
   
    val templateDir = new File("F:/temp/")
    val input = new FileInputStream(new File(templateDir, s"$template.xlsx"))
    val output = new ByteArrayOutputStream()
    try {
      val context = new Context
      model.foreach {
        case (key, value) => context.putVar(key, value)
      }
      context.putVar("items", JavaConverters.seqAsJavaList(Seq(
                    "aaa",
                    "bbb",
                  )))


      context.putVar("sheetNames", JavaConverters.seqAsJavaList(Seq(
                    "a",
                    "b",
                  )));
      
      JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(input, output, context);
      
      val exportFileName = new String(fileName.getBytes("gbk"), "iso-8859-1")
      Result(ResponseHeader(200, Map("Content-Disposition" -> s"attachment;filename=$exportFileName.xls")),
        HttpEntity.Strict(ByteString(output.toByteArray), Some("application/vnd.ms-excel;charset=UTF-8")))
    } finally {
      output.close()
      input.close()
    }
  }


  def exportMerge(headers: Seq[String], data: Seq[Any], template: String, fileName: String,
                 model: Map[String, Object] = Map.empty): Result = {
   
    val templateDir = new File("F:/temp/")
    val input = new FileInputStream(new File(templateDir, s"$template.xlsx"))
    val output = new ByteArrayOutputStream()
    try {
      val context = new Context
      model.foreach {
        case (key, value) => context.putVar(key, value)
      }
      context.putVar("headers", JavaConverters.seqAsJavaList(headers))
      context.putVar("data", JavaConverters.seqAsJavaList(data))
      
      JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(input, output, context);

      val exportFileName = new String(fileName.getBytes("gbk"), "iso-8859-1")
      Result(ResponseHeader(200, Map("Content-Disposition" -> s"attachment;filename=$exportFileName.xls")),
        HttpEntity.Strict(ByteString(output.toByteArray), Some("application/vnd.ms-excel;charset=UTF-8")))
    } finally {
      output.close()
      input.close()
    }
  }




}