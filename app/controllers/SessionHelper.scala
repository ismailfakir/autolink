package controllers

import models.db.{SessionDAO, UserDAO}
import models.record.User
import play.api.mvc.Results.Unauthorized
import play.api.mvc.{AnyContent, EssentialAction, Request, RequestHeader, Result, Security}

import java.time.{LocalDateTime, ZoneOffset}
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

trait SessionHelper {

  val usersDaoImpl: UserDAO

  private def findUser(email: String): Option[User] = {
    val fut = usersDaoImpl.findByEmail(email)
    val maybeUser = Await.result(fut,30.seconds)
    maybeUser
  }

  private def extractUser(req: RequestHeader): Option[User] = {

    val sessionTokenOpt = req.session.get("sessionToken")

    sessionTokenOpt
      .flatMap(token => SessionDAO.getSession(token))
      .filter(_.expiration.isAfter(LocalDateTime.now(ZoneOffset.UTC)))
      .map(_.username)
      .flatMap{ email => findUser(email) }
  }

  protected def withUserSession[T](block: User => Result)(implicit request: Request[AnyContent]): Result = {
    val user = extractUser(request)

    user
      .map(block)
      .getOrElse(Unauthorized(views.html.defaultpages.unauthorized())) // 401, but 404 could be better from a security point of view
  }

}
