package models.ui

import play.api.data.Forms._
import play.api.data.Form

object SampleForm {

  case class Hobby( id: Long, name: String){
    override def toString() = name
  }
  case class Major( id: Long, name: String){
    override def toString() = name
  }
  case class GradeLevel( id: Long, name: String){
    override def toString() = name
  }
  case class GradePointAverage( id: Long, name: String){
    override def toString() = name
  }

  case class Student( id: Long, name: String, password: String, hobbies: Seq[Hobby],level: GradeLevel, majors: Seq[Major]){
    override def toString() = name
  }

  case class Data(name: String, password: String, hobbies: Seq[String],level: String,gpa: String,majors: Seq[String])

  object Roles extends Enumeration {
    val Admin, User = Value
  }

  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "password" -> nonEmptyText,
      "hobbies" -> seq(text),
      "level" -> nonEmptyText,
      "gpa" -> nonEmptyText,
      "majors" -> seq(text),
    )(Data.apply)(Data.unapply)
  )

}
