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

import javax.imageio.ImageIO
import java.io.File
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.{DecodeHintType, BinaryBitmap}
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import java.util

@Singleton
class ZxingController @Inject()(testService: TestService, cc: ControllerComponents) extends AbstractController(cc) {
 
  def index() = Action { implicit request: Request[AnyContent] =>

    val hints = new util.HashMap[DecodeHintType, Any]()
    hints.put(DecodeHintType.TRY_HARDER, true)

    val imageFile = new File("f:\\code.png")
    val image = ImageIO.read(imageFile)
    val bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)))
    val results = new QRCodeMultiReader().decodeMultiple(bitmap, hints)
    Ok(results.foldLeft("")((sum, result) => sum + result.getText))
  }
  
 

}
