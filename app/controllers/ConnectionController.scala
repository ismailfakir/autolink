package controllers

import models.ui.UIHelpers._
import com.fasterxml.jackson.databind.util.JSONPObject
import helpers.utils.DateTimeUtils.now
import helpers.utils.FutureUtils.resultOf
import models.db.{ConnectionDAO, UserDAO}
import models.record.{Connection, User}
import models.ui.{ConnectionForm, MenuGroup}
import models.ui.ConnectionForm._
import org.bson.types.ObjectId
import org.mongodb.scala.bson.{Document, ObjectId}
import play.api.data._
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}
import play.api.libs.json._

class ConnectionController @Inject()(connectionDao: ConnectionDAO, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  private val logger = play.api.Logger(this.getClass)

  private val postUrl = routes.ConnectionController.saveConnection()

  val json: JsValue = Json.parse("""
  {
    "name" : "Watership Down",
    "location" : {
      "lat" : 51.235685,
      "long" : -1.309197
    }
  }
  """)

  val con = Connection("woocommerce",Document.apply(json.toString()))

  def listConnections = Action.async { implicit request =>

    val connections = connectionDao.findAll()
    // views/connection/listConnections.scala.html
    connections.map(c => Ok(views.html.connection.listConnections(MenuGroup.all,c.toList)))
  }

  /**
   * Display the 'new connection form'.
   */

  def addConnection() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.connection.addConnection(MenuGroup.all,form,postUrl))
  }

  // This will be the action that handles our form post
  def saveConnection = Action { implicit request =>
    val errorFunction = { formWithErrors: Form[Data] =>
      println(s"\n\n\n\n ERROR SAVING CONNECTION ${formWithErrors.errors.map(_.message)}")
      BadRequest(views.html.connection.addConnection(MenuGroup.all,formWithErrors,postUrl))
    }

    val successFunction = { data: Data =>

      val newConnection = Connection(data.name,Document.apply(data.config))
      // users += user
      Await.result(connectionDao.insertData(newConnection),30.second)

      Redirect(routes.ConnectionController.listConnections()).flashing("info" -> s"Connection ${newConnection.name} added!")

    }

    val formValidationResult = form.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

  /**
   * Display the 'selected connection form'.
   */
  def editConnection(id: String) = Action.async { implicit request: Request[AnyContent] =>
    val conId = new ObjectId(id)
    connectionDao.findById(conId).map {

      case Some(con) =>

        val putUrl = routes.ConnectionController.updateConnection(id)

        val conForm = ConnectionForm.Data(con.name,con.config.toJson())

        Ok(views.html.connection.editConnection(MenuGroup.all,id,form.fill(conForm),putUrl))

      case None => NotFound
    }

  }

  def updateConnection(id: String) = Action { implicit request: Request[AnyContent] =>

    val errorFunction = { formWithErrors: Form[Data] =>
      val putUrl = routes.ConnectionController.updateConnection(id)
      println(s"\n\n\n\n ERROR \n $formWithErrors")
      BadRequest(views.html.connection.editConnection(MenuGroup.all,id,formWithErrors,putUrl))
    }

    val successFunction = { data: Data =>
      println(s"\n\n\n\n SUCCESS")
      val conId = new ObjectId(id)
      val con = Connection(data.name,Document.apply(data.config)).copy(updatedAt = now())
      Await.result(connectionDao.update(con,conId),30.seconds)

      Redirect(routes.ConnectionController.listConnections()).flashing("info" -> s"Connection ${con.name} updated!")

    }

    val formValidationResult = form.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

  /**
   * Handle connection deletion.
   */
  def deleteConnection(id: String) = Action {
    val conId = new ObjectId(id)

    Await.result(connectionDao.delete(conId),30.seconds)
    Redirect(routes.ConnectionController.listConnections()).flashing("info" -> s"Connection id:${id} deleted!")
  }

}

