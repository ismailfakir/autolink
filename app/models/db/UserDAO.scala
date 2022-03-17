package models.db

import db.MongoDb
import models.record.User
import org.mongodb.scala.{Completed, MongoCollection}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{and, equal}
import org.mongodb.scala.model.FindOneAndUpdateOptions
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.result.DeleteResult
import play.api.Logging

import scala.concurrent.Future

class UserDAO() extends Logging{
  private val userDoc: MongoCollection[User] = MongoDb.users

  def count() = {
    userDoc.countDocuments().toFuture()
  }

  def findAll(): Future[Seq[User]] = {
    userDoc.find().toFuture()
  }

  def findById(id: ObjectId): Future[Option[User]] = {
    userDoc.find(equal("_id", id)).headOption()
  }

  def findByEmail(email: String): Future[Option[User]] = {
    userDoc.find(equal("email", email)).headOption()
  }

  /*def findByUserNameAndPassword(userName: String, password: String): Future[Option[User]] = {
    println(s"$password")
    val query = and(
      equal("name",userName),
      equal("password",password)
    )
    userDoc.find(query).headOption()
  }*/

  def insertData(user: User): Future[Completed] = {
    logger.info(s"inserting new User data:${user}")
    userDoc.insertOne(user)
      .head()
  }

  def insertData(users: Seq[User]): Future[Completed] = {
    logger.info(s"inserting new User data:${users}")
    userDoc.insertMany(users).toFuture()
  }

  def delete(id: ObjectId): Future[DeleteResult] = {
    userDoc.deleteOne(equal("_id", id)).toFuture()
  }

  def update(user: User, id: ObjectId): Future[User] = {

    userDoc
      .findOneAndUpdate(
        equal("_id", id),
        setBsonValue(user),
        FindOneAndUpdateOptions().upsert(true)
      )
      .toFuture()
  }

  private def setBsonValue(user: User): Bson = {
    combine(
      set("name", user.name),
      set("email", user.email),
      set("password", user.password),
      set("role", user.role),
      set("updatedAt", user.updatedAt),
    )
  }

}
