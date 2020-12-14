package controllers

import javax.inject._
import models.db.SampleData
import models.ui.MenuGroup
import play.api._
import play.api.mvc._

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
    val sourceVersion: String = config.get[String]("git.source.versions")
    Ok(views.html.index(sourceVersion))
  }

  def login() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  def logs() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.logs(MenuGroup.all,SampleData.logs))
  }

  def dashboard() = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.dashboard(MenuGroup.all))
  }
}
