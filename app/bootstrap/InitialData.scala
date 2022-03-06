package bootstrap

import javax.inject.Inject
import models.db.{ConnectionDAO, UserDAO}
import models.record.{Connection, User}
import org.mongodb.scala.bson.collection.immutable.Document

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

/** Initial set of data to be imported into the sample application. */
private[bootstrap] class InitialData @Inject() (usersDao: UserDAO, connectionDAO: ConnectionDAO)(implicit executionContext: ExecutionContext) {

  def insertUsers(): Unit = {
    val insertInitialDataFuture = for {
      count <- usersDao.count() if count == 0
      _ <- usersDao.insertData(InitialData.users)
    } yield ()

    Try(Await.result(insertInitialDataFuture, 30.seconds))
  }

  def insertConnections(): Unit = {
    val insertInitialDataFuture = for {
      count <- connectionDAO.count() if count == 0
      _ <- connectionDAO.insertData(InitialData.connections)
    } yield ()

    Try(Await.result(insertInitialDataFuture, 30.seconds))
  }

  insertUsers()
  insertConnections()
}

private[bootstrap] object InitialData {
  def users = Seq(
    User("ismail","open123","ismail7043@yahoo.com","admin")
  )
  def connections = Seq(
    Connection("Ismailsson AB","woo")
  )
}
