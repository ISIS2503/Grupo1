package models

import play.api.libs.json.Json

object JsonFormats {
  implicit val measurementFormat = Json.format[Measurement]
  implicit val alertFormat = Json.format[Alert]
}
