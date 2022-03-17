package tasks

import scala.concurrent._
import javax.inject._
import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import io.github.ismailfakir.scalacommon.http._
import org.json4s.{JNothing, JValue}
import play.api.libs.json.JsValue
import play.api.libs.json.Json

import scala.util.Success

@Singleton
class HelloTask @Inject()(actorSystem: ActorSystem)(implicit ec: ExecutionContext){
  actorSystem.scheduler.scheduleAtFixedRate(initialDelay = 100.minutes, interval = 100.minutes) { () =>
    //process()
    memoryInfo()
  }
  def process(): Unit = {
    println("This originally executed 1 minutes after the server started and will execute again in 1 minutes")
  }

  def memoryInfo(): Unit = {

    println("********************************************************************")
    println("*****                        CALLING rest api                  *****")
    println("********************************************************************")

    val request = HttpClient
      .get("https://api.open-meteo.com/v1/forecast?latitude=55.6763&longitude=12.5681&daily=sunrise,sunset&timezone=Europe%2FLondon")
      .asJson()
    val response = Await.result(request,30.seconds)
    response match {
      case (_,Success(j)) => println(Json.prettyPrint(j))
      case _ => println("something went worng!")
    }

    /*// memory info
    val mb = 1024*1024
    val runtime = Runtime.getRuntime
    println("********************************************************************")
    println("*****                        Memory Uses                       *****")
    println("********************************************************************")
    println("ALL RESULTS IN MB")
    println("** Used Memory:  " + (runtime.totalMemory - runtime.freeMemory) / mb)
    println("** Free Memory:  " + runtime.freeMemory / mb)
    println("** Total Memory: " + runtime.totalMemory / mb)
    println("** Max Memory:   " + runtime.maxMemory / mb)*/
  }
}