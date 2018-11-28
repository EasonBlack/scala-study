package eason.web

import play.api.libs.json.{JsValue, Json, Writes}

object ApiResponse {

  implicit val responseWrites = new Writes[ApiResponse] {
    override def writes(res: ApiResponse): JsValue = res.data match {
      case None => Json.obj(     
        "code" -> 0, // for project compatibility
        "message" -> res.message
      )
      case Some(d) => Json.obj(
        "data" -> d,
        "message" -> res.message
      )
    }
  }

  def ok(data: Option[JsValue] = None) = ApiResponse(data = data)

 
}


case class ApiResponse(data: Option[JsValue] = None, message: String = "OK") {
}
