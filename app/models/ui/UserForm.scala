package models.ui
import play.api.data.Forms._
import play.api.data.Form

object UserForm {

  case class Data(name: String, email: String, password: String, role: String)

  object Roles extends Enumeration {
    val Admin, User = Value
  }

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "role" -> nonEmptyText,
    )(Data.apply)(Data.unapply)
  )

}
