package controllers

import helpers.utils.FutureUtils.resultOf
import helpers.utils.PasswordHash.{createHash, validatePassword}

import javax.inject._
import models.db.{SampleData, SessionDAO, UserDAO}
import models.record.User
import models.ui.{LoginForm, MenuGroup}
import play.api._
import play.api.data.Form
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._
import models.ui.LoginForm.Data

import java.time.{LocalDateTime, ZoneOffset}
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.sys.process.Process

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val usersDao: UserDAO,val config: Configuration, val c: ControllerComponents) extends AbstractController(c) with SessionHelper {

  override val usersDaoImpl = usersDao

  implicit val loginFormFormat = Json.format[Data]

  def index() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.login())
  }

  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(
      views.html.loginvue(
        MenuGroup.none,serverInfo()
      )
    )
  }

  def loginUserJson = Action(parse.json) { request =>
  println(s"${request.body.toString()}")
    val placeResult = request.body.validate[Data]
    placeResult.fold(
      errors => {
        BadRequest(Json.obj("message" -> JsError.toJson(errors)))
      },
      user => {
        val fut = usersDao.findByEmail(user.email)
        val maybeUser = Await.result(fut,30.seconds)
        maybeUser match {
          case Some(value) if validatePassword(user.password,value.password) =>

            println(s"got a corect user:$value")

            Redirect(routes.HomeController.dashboard()).flashing("info" -> s"User ${value.name} loggedin!")
          case _ => Ok(Json.obj("message" -> "User Not Found!!!"))
        }
      }
    )
  }

  def loginUser = Action { implicit request =>
    println("Handling request")
    val errorFunction = { formWithErrors: Form[Data] =>
      println(s"GOT BAD in Handling request:${formWithErrors.errors.mkString(" ")}")
      Redirect(routes.HomeController.login()).flashing("error" -> s"User not found! ${formWithErrors.errors.map(e =>e.key+ e.messages)}")
    }

    val successFunction = { data: Data =>

      val fut = usersDao.findByEmail(data.email)
      val maybeUser = Await.result(fut,30.seconds)
      maybeUser match {
        case Some(value) if validatePassword(data.password,value.password) =>

          val token = SessionDAO.generateToken(value.email)

          Redirect(routes.HomeController.dashboard()).flashing("info" -> s"User ${value.name} loggedin!")
            .withSession(request.session + ("sessionToken" -> token))
        case _ =>
          //NotFound(views.html.loginvue(MenuGroup.none,serverInfo())).flashing("error" -> s"User not found!")
          Redirect(routes.HomeController.login()).flashing("error" -> s"User not found!")
          .withNewSession
      }

    }

    val formValidationResult = LoginForm.form.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }


  def logs() = Action { implicit request: Request[AnyContent] =>

    withUserSession(u => Ok(views.html.logs(MenuGroup.all,SampleData.logs)))
  }

  def dashboard() = Action { implicit request: Request[AnyContent] =>
    val url = config.get[String]("fortnox.auth_url")
    val token = config.get[String]("fortnox.access_token")
    println(s"TOKEN $token")

    println(s"URL $url")
    withUserSession(u => Ok(views.html.dashboard(MenuGroup.all)))
  }

  def serverInfo() = {
    val commitHash = Process(Seq("git", "rev-parse", "HEAD")).!!.trim
    val gitBranch = Process(Seq("git", "branch")).!!.trim
    Json.obj(
      "branch" -> gitBranch.replace("*","").trim,
      "commitHash" -> commitHash
    )
  }

  def fortnoxCallback() = Action { implicit request: Request[AnyContent] =>
    def param(field: String): Option[String] =
      request.queryString.get(field).flatMap(_.headOption)

    println(s" fortnox call back authcode :${param("code")}")



    Redirect(routes.HomeController.login())
  }

}
