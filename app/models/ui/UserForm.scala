package models.ui
import play.api.data.Forms._
import play.api.data.Form

object UserForm {

  case class Data(userName: String, password: String)

  val form = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

}
