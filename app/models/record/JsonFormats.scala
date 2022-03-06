package models.record
import java.time.LocalDateTime
import helpers.utils.DateTimeUtils.defaultDateFormat
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.collection.immutable.Document
import play.api.libs.json._

import scala.util.Try

object JsonFormats {

  implicit val documentFormat = DocumentFormatJsonMacro
  implicit val localDateTimeFormat = localDateTimeFormatJsonMacro
  implicit val objectIdFormat = ObjectIdFormatJsonMacro

  implicit val userFormat = Json.format[User]
  implicit val connectionFormat = Json.format[Connection]
  implicit val userOldFormat = Json.format[UserOld]
  implicit val employeeFormat = Json.format[Employee]
}

/* Document format */
object DocumentFormatJsonMacro extends Format[Document] {

  def reads(json: JsValue) = {
    val str = json.as[String]
    val doc = Document(str)
    JsSuccess(doc)
  }
  def writes(document: Document) = JsString(document.toJson())
}

/* LocalDateTime format*/
object localDateTimeFormatJsonMacro extends Format[LocalDateTime] {
  val format = defaultDateFormat
  def reads(json: JsValue) = {
    val str = json.as[String]
    val ldt=LocalDateTime.parse(str,format)
    JsSuccess(ldt)
  }
  def writes(localDateTime: LocalDateTime) = JsString(format.format(localDateTime))
}

/* objectId format */
object ObjectIdFormatJsonMacro extends Format[ObjectId] {

  def writes(objectId: ObjectId): JsValue = JsString(objectId.toString)
  def reads(json: JsValue): JsResult[ObjectId] = json match {
    case JsString(x) => {
      val maybeOID: Try[ObjectId] = Try{new ObjectId(x)}
      if(maybeOID.isSuccess) JsSuccess(maybeOID.get) else {
        JsError("Expected ObjectId as JsString")
      }
    }
    case _ => JsError("Expected ObjectId as JsString")
  }
}
