package tasks
import javax.inject._
import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

@Singleton
class HelloTask @Inject()(actorSystem: ActorSystem)(implicit ec: ExecutionContext){
  actorSystem.scheduler.scheduleAtFixedRate(initialDelay = 30.minutes, interval = 30.minutes) { () =>
    //process()
    memoryInfo()
  }
  def process(): Unit = {
    println("This originally executed 1 minutes after the server started and will execute again in 1 minutes")
  }

  def memoryInfo(): Unit = {
    // memory info
    val mb = 1024*1024
    val runtime = Runtime.getRuntime
    println("********************************************************************")
    println("*****                        Memory Uses                       *****")
    println("********************************************************************")
    println("ALL RESULTS IN MB")
    println("** Used Memory:  " + (runtime.totalMemory - runtime.freeMemory) / mb)
    println("** Free Memory:  " + runtime.freeMemory / mb)
    println("** Total Memory: " + runtime.totalMemory / mb)
    println("** Max Memory:   " + runtime.maxMemory / mb)
  }
}