package controllers

import java.time.LocalDate
import java.util.UUID
import helpers.utils.FutureUtils.resultOf

import javax.inject.Inject
import models.db.{ConnectionDAO, EmployeeDAO, UserDAO}
import models.record.{Employee, User}
import models.ui.MenuGroup
import models.ui.UserForm._
import play.api.data._
import play.api.mvc._
import play.api.http.MimeTypes
import play.api.routing._
import play.api.libs.json.Json

import scala.collection.mutable
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

class JsRoutesController  @Inject()(connectionDao: ConnectionDAO, cc: ControllerComponents)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def getBackendMessage = Action.async { implicit request =>
    val address = request.remoteAddress
    Future.successful(
      Ok(Json.toJson(s"your ip address is $address which is collected by server Scala code but shown in UI by client(browser) Javascript code"))
    )
  }

  def get = Action.async { implicit request =>
    val address = request.remoteAddress
    Future.successful(
      Ok(Json.toJson(s"your ip address is $address which is collected by server Scala code but shown in UI by client(browser) Javascript code"))
    )
  }

  def jsRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.JsRoutesController.getBackendMessage
      )
    ).as(MimeTypes.JAVASCRIPT)
  }

}
