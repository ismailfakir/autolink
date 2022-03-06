package models.ui
import play.api.data.Forms._
import play.api.data.Form

object UserForm {

  case class Data(name: String, email: String, password: String, role: String)
  case class Data1(id: String, name: String, email: String, password: String, role: String)

  object Roles extends Enumeration {
    val Admin, User = Value
  }

  val form = Form(
    mapping(
      "name" -> nonEmptyText(minLength = 3),
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "role" -> nonEmptyText,
    )(Data.apply)(Data.unapply)
  )

  val form1 = Form(
    mapping(
      "id" -> nonEmptyText,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText,
      "role" -> nonEmptyText,
    )(Data1.apply)(Data1.unapply)
  )

}
