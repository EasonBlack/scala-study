
package org.example.hello.api

import java.util.Date

import play.api.libs.json.{Format, Json}

object Brand {
  implicit val format: Format[Brand] = Json.format
}

case class Brand(id: Int, name: String) {}