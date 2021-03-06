package models.db

import javax.inject.{ Inject, Singleton }

import scala.concurrent.{ ExecutionContext, Future }
import models.record.UserOld
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile

trait UsersComponent { self: HasDatabaseConfigProvider[JdbcProfile] =>
  import profile.api._

  class Users(tag: Tag) extends Table[UserOld](tag, "user") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def password = column[String]("password")
    def * = (id.?, name, password) <> (UserOld.tupled ,UserOld.unapply _)
  }
}

@Singleton()
class UserDaoOld @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext)
  extends UsersComponent
    with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val users = TableQuery[Users]

  /** Construct the Map[String,String,String] needed to fill a select options set */
  def options(): Future[Seq[(String, String,String)]] = {
    val query = (for {
      user <- users
    } yield (user.id, user.name,user.password)).sortBy( /*name*/ _._2)

    db.run(query.result).map(rows => rows.map { case (id, name,password) => (id.toString, name,password) })
  }

  /** Insert a new user */
  def insert(user: UserOld): Future[Unit] =
    db.run(users += user).map(_ => ())

  /** Insert new users */
  def insert(users: Seq[UserOld]): Future[Unit] =
    db.run(this.users ++= users).map(_ => ())

  def count(): Future[Int]=
    db.run(this.users.map(_.id).length.result)

  /** Return a page of (Computer,Company) */
  def listAll(): Future[Seq[UserOld]] = {
    db.run(this.users.result)
  }
}
