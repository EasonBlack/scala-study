package eason.controllers

import eason.web.ApiResponse
import com.google.inject.Inject
import play.api.libs.json.{JsPath, JsValue, Json, JsonValidationError}
import play.api.mvc.{InjectedController, Request, Result}

import scala.concurrent.ExecutionContext

abstract class BaseController @Inject()(implicit exec: ExecutionContext)
  extends InjectedController {

  protected def apiResult(res: ApiResponse): Result = {
    val resp = Json.toJson(res)
    Ok(resp)
  }

  protected def dataResult(data: JsValue): Result = apiResult(ApiResponse.ok(Some(data)))

  protected def emptyResult = apiResult(ApiResponse.ok())

}
