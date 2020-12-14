package models.db

import java.util.{Calendar, Date}

case class Log(id: Int, date: Date, ip:String, request: String)

object SampleData {

  val now = Calendar.getInstance().getTime()

  val logs = (1 to 100).toList
    .map(i => Log(i,now,s"192.168.1.$i",s"https://autolink365.herokuapp.com/request/dashboard/logs/log$i.html"))
    .toList

}
