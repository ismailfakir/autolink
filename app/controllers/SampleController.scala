package controllers

import javax.inject._
import models.ui.MenuGroup
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class SampleController @Inject()(val config: Configuration, val c: ControllerComponents) extends AbstractController(c) {

  def blank() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.blank(MenuGroup.all))
  }

  def invoice() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.invoice(MenuGroup.all))
  }

}