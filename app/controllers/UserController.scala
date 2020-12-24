package controllers
import java.time.LocalDate
import java.util.UUID

import javax.inject.Inject
import models.db.{EmployeeDAO, UserDAO}
import models.record.{Employee, User}
import models.ui.MenuGroup
import models.ui.UserForm._
import play.api.data._
import play.api.mvc._

import scala.collection.mutable
import scala.concurrent.ExecutionContext

class UserController @Inject()(usersDao: UserDAO, employeeDAO: EmployeeDAO, cc: ControllerComponents)(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) with play.api.i18n.I18nSupport
{

  private val users = mutable.ArrayBuffer(
    User(None,"Widget 1", "123"),
    User(None,"Widget 2", "123"),
    User(None,"Widget 3", "123"),
    User(None,"Widget 4", "123"),
  )

  private val postUrl = routes.UserController.createUser()

  def listUsers = Action.async { implicit request =>
    // Pass an unpopulated form to the template
    //Ok(views.html.listUsers(MenuGroup.all,users.toList, form, postUrl))
    val emp = Employee(UUID.randomUUID().toString,"jave", LocalDate.now)
    employeeDAO.insertData(emp)
    val mployees = employeeDAO.findAll()


    val dbUsers = usersDao.listAll()
    dbUsers.map(u => Ok(views.html.listUsers(MenuGroup.all,u.toList, form, postUrl)))
  }

  // This will be the action that handles our form post
  def createUser = Action { implicit request =>
    val errorFunction = { formWithErrors: Form[Data] =>
      // This is the bad case, where the form had validation errors.
      // Let's show the user the form again, with the errors highlighted.
      // Note how we pass the form with errors to the template.
      BadRequest(views.html.listUsers(MenuGroup.all,users.toList, formWithErrors, postUrl))
    }

    val successFunction = { data: Data =>
      // This is the good case, where the form was successfully parsed as a Data object.
      val user = User(id = None, name = data.userName, password = data.password)
      users += user
      Redirect(routes.UserController.listUsers()).flashing("info" -> s"User ${user.name} added!")
    }

    val formValidationResult = form.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

}
