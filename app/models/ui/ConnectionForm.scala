package models.ui
import play.api.data.Forms._
import play.api.data.Form

object ConnectionForm {

  case class Data(name: String, config: String)

  val form = Form(
    mapping(
      "name" -> nonEmptyText(minLength = 3),
      "config" -> default(nonEmptyText,"{}")
    )(Data.apply)(Data.unapply)
  )

}
