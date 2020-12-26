package bootstrap

import javax.inject.Inject
import models.db.UserDaoOld
import models.record.UserOld

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.util.Try

/** Initial set of data to be imported into the sample application. */
private[bootstrap] class InitialData @Inject() (usersDaoOld: UserDaoOld)(implicit executionContext: ExecutionContext) {

  def insert(): Unit = {
    val insertInitialDataFuture = for {
      count <- usersDaoOld.count() if count == 0
      _ <- usersDaoOld.insert(InitialData.users)
    } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insert()
}

private[bootstrap] object InitialData {
  def users = Seq(
    UserOld(Option(1L),"ismail","open123"),
    UserOld(Option(2L),"javed","jfhdkjhgsj")
  )
}
