package models.record

import io.github.ismailfakir.scalacommon.json.JsonHelpers.JsLookupResultHelpers
import play.api.libs.json.{JsValue, Json}
import io.github.ismailfakir.scalacommon.json._

class WoocommerceConfig(code: String,url: String, apiUser: String, apiPassword: String) extends SystemConfig {
  override val codeName = "woocommerce"

  def apply(url: String, apiUser: String, apiPassword: String): WoocommerceConfig = {
    new WoocommerceConfig(codeName,url,apiUser,apiPassword)
  }

  def toJson(): JsValue = {
    Json.obj(
      "code" -> codeName,
      "url" -> url,
      "apiUser" -> apiUser,
      "apiPassword" -> apiPassword,
    )
  }

}
object WoocommerceConfig {
  def fromJson(json:JsValue): WoocommerceConfig = {
    new WoocommerceConfig(
      (json \ "code").asStr.getOrElse(""),
      (json \ "url").asStr.getOrElse(""),
      (json \ "apiUser").asStr.getOrElse(""),
      (json \ "apiPassword").asStr.getOrElse("")
    )
  }
}
