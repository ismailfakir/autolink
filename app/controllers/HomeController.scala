package controllers

import javax.inject._
import models.db.SampleData
import models.ui.MenuGroup
import play.api._
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.mvc._

import scala.sys.process.Process

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val config: Configuration, val c: ControllerComponents) extends AbstractController(c) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    //val sourceVersion: String = config.get[String]("git.source.versions")
    //val sourceVersion: String = "ls -al" !!
    val sourceVersion = Process(Seq("git", "rev-parse", "HEAD")).!!.trim
    val gitBranch = Process(Seq("git", "branch")).!!.trim
    Ok(views.html.index(MenuGroup.none,gitBranch,sourceVersion))
    //Ok(views.html.dashboard(MenuGroup.all))
  }

  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(
      views.html.loginvue(
        MenuGroup.none,serverInfo()
      )
    )
  }

  def logs() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.logs(MenuGroup.all,SampleData.logs))
  }

  def dashboard() = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.dashboard(MenuGroup.all))
  }

  def serverInfo() = {
    val commitHash = Process(Seq("git", "rev-parse", "HEAD")).!!.trim
    val gitBranch = Process(Seq("git", "branch")).!!.trim
    Json.obj(
      "branch" -> gitBranch.replace("*","").trim,
      "commitHash" -> commitHash
    )
  }
}
