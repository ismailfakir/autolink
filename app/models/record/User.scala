package models.record


import java.time.LocalDateTime

import helpers.utils.DateTimeUtils._
import helpers.utils.PasswordHash._
import org.mongodb.scala.bson.ObjectId

case class User (_id: ObjectId, name: String, email: String, password: String, role: String, createdAt: LocalDateTime , updatedAt: LocalDateTime)

object User {

  def apply(name: String, email: String, password: String, role: String): User =
    new User(
      new ObjectId(),
      name,
      email,
      createHash(password),
      role,
      now(),
      now()
    )
}

