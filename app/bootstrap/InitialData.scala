package bootstrap

import javax.inject.Inject

import models.db.UserDAO
import models.record.User

import scala.concurrent.{ Await, ExecutionContext }
import scala.concurrent.duration.Duration
import scala.util.Try

/** Initial set of data to be imported into the sample application. */
private[bootstrap] class InitialData @Inject() (usersDao: UserDAO)(implicit executionContext: ExecutionContext) {

  def insert(): Unit = {
    val insertInitialDataFuture = for {
      count <- usersDao.count() if count == 0
      _ <- usersDao.insert(InitialData.users)
    } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insert()
}

private[bootstrap] object InitialData {
  def users = Seq(
    User(Option(1L),"ismail","open123"),
    User(Option(2L),"javed","jfhdkjhgsj")
  )
}
