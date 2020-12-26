package helpers.utils

import scala.concurrent.{Await, Awaitable}
import scala.concurrent.duration.{DurationInt, FiniteDuration}

object FutureUtils {

  def resultOf[A](awaitable: Awaitable[A], max: FiniteDuration = 3.seconds) =
    Await.result(awaitable, max)

}
