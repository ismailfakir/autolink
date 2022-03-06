package controllers

import java.time.LocalDate
import java.util.UUID
import helpers.utils.FutureUtils.resultOf

import javax.inject.Inject
import models.db.{ConnectionDAO, EmployeeDAO, UserDAO}
import models.record.{Connection, Employee, User}
import models.ui.MenuGroup
import models.ui.UserForm._
import play.api.data._
import play.api.mvc._
import play.api.http.MimeTypes
import play.api.routing._
import play.api.libs.json.{Json, Writes}

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Success

import models.record.JsonFormats._

class JsRoutesController  @Inject()(connectionDao: ConnectionDAO, cc: ControllerComponents)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  def getBackendMessage = Action.async { implicit request =>
    val address = request.remoteAddress
    Future.successful(
      Ok(Json.toJson(s"your ip address is $address which is collected by server Scala code but shown in UI by client(browser) Javascript code"))
    )
  }

  def getConnections = Action.async { implicit request =>
    val fut:Future[Seq[Connection]] = connectionDao.findAll()

    fut
      .map { cons => Ok(Json.toJson(cons)) }
  }

  def jsRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        routes.javascript.JsRoutesController.getBackendMessage,
        routes.javascript.JsRoutesController.getConnections,
      )
    ).as(MimeTypes.JAVASCRIPT)
  }

}
