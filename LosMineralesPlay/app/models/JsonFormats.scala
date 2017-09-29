package models

import play.api.libs.json.Json

object JsonFormats {
  implicit val measurementFormat = Json.format[Measurement]
}
