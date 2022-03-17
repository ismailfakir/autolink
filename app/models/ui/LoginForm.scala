package models.ui
import play.api.data.Forms._
import play.api.data.Form

object LoginForm {

  case class Data(email: String, password: String)

  val form = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
      //"password" -> nonEmptyText(minLength = 8).verifying("atleas 8 char",_.length >8 )
    )(Data.apply)(Data.unapply)
  )

}
