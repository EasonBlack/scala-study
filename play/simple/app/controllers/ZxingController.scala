package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.http.HttpEntity
import akka.util.ByteString
import play.api.libs.json._
import play.api.mvc.AnyContent
import services._
import sys.process._
import javax.imageio.ImageIO
import java.io.{File, FileOutputStream, BufferedInputStream, BufferedOutputStream}
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.{DecodeHintType, BinaryBitmap}
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import java.util
import java.net.{MalformedURLException, URL}

import java.util.UUID



@Singleton
class ZxingController @Inject()(testService: TestService, cc: ControllerComponents) extends AbstractController(cc) {
 
  def view() =Action { implicit request: Request[AnyContent] =>
      Ok(views.html.zxing())
  }
  

  def index() = Action { implicit request: Request[AnyContent] =>

    val hints = new util.HashMap[DecodeHintType, Any]()
    hints.put(DecodeHintType.TRY_HARDER, true)

    val imageFile = new File("f:\\code.png")
    val image = ImageIO.read(imageFile)
    val bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)))
    val results = new QRCodeMultiReader().decodeMultiple(bitmap, hints)
    Ok(results.foldLeft("")((sum, result) => sum + result.getText))
  }
  

   def index2() = Action { implicit request: Request[AnyContent] =>

    Ok("aaa")
    // val hints = new util.HashMap[DecodeHintType, Any]()
    // hints.put(DecodeHintType.TRY_HARDER, true)

    // // val imageFile = new URL("http://pic31.nipic.com/20130709/13191243_104542012332_2.jpg") #> new File("f:\\test2.jpg").!!
    // val imageFile = new FileOutputStream("http://pic31.nipic.com/20130709/13191243_104542012332_2.jpg")
    // //val imageFile = new File("f:\\test.jpg")
    // // val image = new BufferedOutputStream(imageFile)
    // val image = ImageIO.read(imageFile)
    // val bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)))
    // val results = new QRCodeMultiReader().decodeMultiple(bitmap, hints)
    // Ok(results.foldLeft("")((sum, result) => sum + result.getText))
  }

  def updatefile() = Action(parse.multipartFormData) { request =>
    request.body.file("file") match {
      case None => Ok("false")
      case Some(file) => {
        val fileName = UUID.randomUUID().toString
        val filePath = s"f:\\$fileName.png"
        println(fileName)
        val data = java.nio.file.Files.readAllBytes(file.ref)
        val stream = new FileOutputStream(filePath)
        try {
          stream.write(data)
        } finally {
          stream.close()
        }
        Ok("true")
      }
    }
   
  }

  def testZxing() = Action(parse.multipartFormData) { request =>
    request.body.file("file") match {
      case None => Ok("false")
      case Some(file) => {
        val fileName = UUID.randomUUID().toString
        val filePath = s"f:\\$fileName.png"
        println(fileName)

        val hints = new util.HashMap[DecodeHintType, Any]()
        hints.put(DecodeHintType.TRY_HARDER, true)
        val image = ImageIO.read(file.ref)
       
        val bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)))
        try {
          val results = new QRCodeMultiReader().decodeMultiple(bitmap, hints)
          Ok(results.foldLeft("")((sum, result) => sum + result.getText))
        } catch {
          case _  => {
            Ok("False")
          }
               
        }
      
    
      }
    }
   
  }
 

}
