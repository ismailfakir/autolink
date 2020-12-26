package helpers.utils

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId}

object DateTimeUtils {
  val defaultDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
  val defaultZoneId = ZoneId.of("Europe/Paris")

  def now(): LocalDateTime = {
    LocalDateTime.now(defaultZoneId)
  }

  def toFormattedString(time: LocalDateTime): String = {
    time.format(defaultDateFormat)
  }
}
