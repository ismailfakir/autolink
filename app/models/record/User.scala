package models.record
import play.api.libs.json._
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}
case class User (id: Option[Long], name: String, password: String)
object JsonFormats {

  implicit val userFormat = Json.format[User]
}