package controllers

/*import autolink.proto.addressbook.Person
import autolink.proto.meeting.Meeting*/
import com.google.protobuf.duration.Duration
import scalapb.json4s.JsonFormat
import play.api.libs.json._

import javax.inject._
import models.ui.MenuGroup
import play.api._
import play.api.mvc._
import models.ui.SampleForm._

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SampleController @Inject()(val config: Configuration, val c: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(c) with play.api.i18n.I18nSupport {

  private val postUrl = routes.SampleController.example()

  /*val person = Person.defaultInstance
    .withName("ismail")
  val r: String = JsonFormat.toJsonString(person)
  val p: Person = JsonFormat.fromJsonString[Person](r)
  val meeting = Meeting.defaultInstance*/


  def blank() = Action { implicit request: Request[AnyContent] =>
  println(s"")
    Ok(views.html.blank(MenuGroup.all))
  }

  def example() = Action { implicit request: Request[AnyContent] =>

    val hobbies: Map[String,Boolean] = Map(("fishing",false),("football",true))
    val levels: List[String] = List("Advanced","Midium","Entry")
    val gpa: Map[String,Boolean] = Map(("4",true),("5",false))
    val majors: Map[String,Boolean] = Map(("Accounting",true),("IT",false))
    Ok(views.html.example(
      MenuGroup.all,
      form,
      hobbies,
      levels,
      gpa,
      majors,
      postUrl
    )
    )
  }

  def vueExample() = Action { implicit request: Request[AnyContent] =>
  val json = Json.obj(
    "name" -> "ismail",
    "price" -> 123
  )

    Ok(views.html.vueexample(MenuGroup.all,json))
  }

  def invoice() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.invoice(MenuGroup.all))
  }

}