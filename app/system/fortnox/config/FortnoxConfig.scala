package system.fortnox.config

import play.api.libs.json.{JsValue, Json}
import io.github.ismailfakir.scalacommon.json.JsonHelpers._
import system.SystemsUtils

case class FortnoxConfig(
    codeName: String = SystemsUtils.fortnoxCodeName,
    clientId: String = "",
    clientSecret: String = "",
) {
  def toJson(): JsValue = {
    Json.obj(
      "codeName" -> codeName,
      "clientId" -> clientId ,
      "clientSecret" -> clientSecret
    )
  }
}

object FortnoxConfig {
  def fromJson(js: JsValue): FortnoxConfig = {
    FortnoxConfig(
      (js \ "codeName").asStr().getOrElse(SystemsUtils.fortnoxCodeName),
      (js \ "clientId").asStr.getOrElse(""),
      (js \ "clientId").asStr.getOrElse("")
    )
  }
}
