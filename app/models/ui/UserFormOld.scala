package models.ui
import play.api.data.Forms._
import play.api.data.Form

object UserFormOld {

  case class Data(userName: String, password: String)

  val form = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )

}
