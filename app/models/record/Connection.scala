package models.record

import java.time.LocalDateTime
import helpers.utils.DateTimeUtils._
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document
import play.api.libs.json.{JsValue, Json}
import io.github.ismailfakir.scalacommon.time.DateUtils.defaultDateTimeformatter

case class Connection (_id: ObjectId, name: String, config: Document, createdAt: LocalDateTime, updatedAt: LocalDateTime){
  def toJson(): JsValue = {
    val dateTimeFormat = defaultDateTimeformatter
    Json.obj(
      "id" -> _id.toString,
      "name" -> name,
      "config" -> Json.parse(config.toJson()),
      "createdAt" -> createdAt.format(dateTimeFormat),
      "updatedAt" -> updatedAt.format(dateTimeFormat)
    )
  }
}

object Connection {

  def defaultConfig(code: String = "code") = {

    val cfgJson = Json.obj(
      "code" -> code,
      "url" -> "url",
      "apiUser" -> "api user",
      "apiPassword" -> "api password",
    )

    Document(cfgJson.toString())
  }

  def apply(name: String, config: Document = defaultConfig()): Connection =
    new Connection(
      new ObjectId(),
      name,
      config,
      now(),
      now()
    )

  def apply(name: String, code: String): Connection =
    new Connection(
      new ObjectId(),
      name,
      defaultConfig(code),
      now(),
      now()
    )
}
