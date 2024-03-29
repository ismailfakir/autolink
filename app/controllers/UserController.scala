package controllers
import helpers.utils.DateTimeUtils.now

import java.time.LocalDate
import java.util.UUID
import helpers.utils.FutureUtils.resultOf

import javax.inject.Inject
import models.db.{EmployeeDAO, UserDAO}
import models.record.{Employee, User}
import models.ui.MenuGroup
import models.ui.UserForm._
import org.bson.BsonObjectId
import org.mongodb.scala.bson
import org.mongodb.scala.bson.ObjectId
import play.api.data._
import play.api.mvc._
import play.api.http.MimeTypes
import play.api.routing._
import play.api.libs.json.Json

import scala.collection.mutable
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

class UserController @Inject()(usersDao: UserDAO, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with play.api.i18n.I18nSupport
{
  private val logger = play.api.Logger(this.getClass)

  private val postUrl = routes.UserController.createUser()
  private val putUrl = routes.UserController.editUser()

  def listUsers = Action.async { implicit request =>

    val dbUsers = usersDao.findAll()
    dbUsers.map(u => Ok(views.html.listUsers(MenuGroup.all,u.toList, form,form1, postUrl,putUrl)))
  }

  // This will be the action that handles our form post
  def createUser = Action { implicit request =>
    val errorFunction = { formWithErrors: Form[Data] =>
      // This is the bad case, where the form had validation errors.
      // Let's show the user the form again, with the errors highlighted.
      // Note how we pass the form with errors to the template.
    val users = resultOf(usersDao.findAll())
      BadRequest(views.html.listUsers(MenuGroup.all,users.toList, formWithErrors, form1, postUrl,putUrl))
    }

    val successFunction = { data: Data =>
      // This is the good case, where the form was successfully parsed as a Data object.
    logger.info(s"\n\n\n\n creating user ==============================> ${data.name}")
      val user = User(data.name, data.email,data.password,data.role)
      // users += user
      Await.result(usersDao.insertData(user),30.second)

      Redirect(routes.UserController.listUsers()).flashing("info" -> s"User ${user.name} added!")

    }

    val formValidationResult = form.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

  // This will be the action that handles our form post
  def editUser() = Action { implicit request =>
    val errorFunction = { formWithErrors: Form[Data1] =>
      println(s"\n\n\n\n editing user ==============================> ${formWithErrors.data}")
      // This is the bad case, where the form had validation errors.
      // Let's show the user the form again, with the errors highlighted.
      // Note how we pass the form with errors to the template.
      val users = resultOf(usersDao.findAll())
      BadRequest(views.html.listUsers(MenuGroup.all,users.toList, form,formWithErrors, postUrl,putUrl))
    }

    val successFunction = { data: Data1 =>
      println(s"\n\n\n\n editing user ==============================> ${data.id}")
      // This is the good case, where the form was successfully parsed as a Data object.
      val user = User(data.name, data.email,data.password,data.role).copy(updatedAt = now())
      // users += user
      val oid = new ObjectId(data.id)

      Await.result(usersDao.update(user,oid),30.second)

      Redirect(routes.UserController.listUsers()).flashing("info" -> s"User ${user.name} edited!")

    }

    val formValidationResult = form1.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

  def deleteUser(id: String) = Action { implicit request =>

    val oid = new ObjectId(id)
    Await.result(usersDao.delete(oid),30.second)

    Redirect(routes.UserController.listUsers()).flashing("info" -> s"User ${id} deleted!")
  }

}
