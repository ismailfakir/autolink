package models.db

import db.MongoDb
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.FindOneAndUpdateOptions
import org.mongodb.scala.model.Updates.{combine, set}
import org.mongodb.scala.result.DeleteResult
import org.mongodb.scala.{Completed, MongoCollection}
import models.record.JsonFormats._
import models.record.Employee

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import play.api.Logging

class EmployeeDAO() extends Logging {

  private val employeeDoc: MongoCollection[Employee] = MongoDb.employees

  def findAll(): Future[Seq[Employee]] = {
    employeeDoc.find().toFuture()
  }

  def insertData(emp: Employee): Future[String] = {
    logger.info(s"inserting data:${emp}")
    employeeDoc.insertOne(emp)
      .head()
      .map { _ => emp._id }
  }

}
