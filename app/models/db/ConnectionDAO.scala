package models.db

import db.MongoDb
import models.record.Connection
import org.mongodb.scala.{Completed, MongoCollection}
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.FindOneAndUpdateOptions
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.result.DeleteResult
import play.api.Logging

import scala.concurrent.Future

class ConnectionDAO() extends Logging {
  private val connectionDoc: MongoCollection[Connection] = MongoDb.connections

  def count() = {
    connectionDoc.countDocuments().toFuture()
  }

  def findAll(): Future[Seq[Connection]] = {
    connectionDoc.find().toFuture()
  }

  def findById(id: ObjectId): Future[Option[Connection]] = {
    connectionDoc.find(equal("_id", id)).headOption()
  }

  def insertData(connection: Connection): Future[Completed] = {
    logger.info(s"inserting new Connection data:${connection}")
    connectionDoc.insertOne(connection)
      .head()
  }

  def insertData(connections: Seq[Connection]): Future[Completed] = {
    logger.info(s"inserting new User data:${connections}")
    connectionDoc.insertMany(connections).toFuture()
  }

  def delete(id: ObjectId): Future[DeleteResult] = {
    connectionDoc.deleteOne(equal("_id", id)).toFuture()
  }

  def update(connection: Connection, id: ObjectId): Future[Connection] = {

    connectionDoc
      .findOneAndUpdate(
        equal("_id", id),
        setBsonValue(connection),
        FindOneAndUpdateOptions().upsert(true)
      )
      .toFuture()
  }

  private def setBsonValue(connection: Connection): Bson = {
    combine(
      set("name", connection.name),
      set("config", connection.config),
      set("updatedAt", connection.updatedAt),
    )
  }

}

