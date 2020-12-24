package db

import ch.qos.logback.classic.{Level, Logger}
import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

trait MongodbConfig {

  lazy val url = rootConfig.getString("mongo.url")

  lazy val databaseName = rootConfig.getString("mongo.database")

  lazy val databaseUser = rootConfig.getString("mongo.user")

  lazy val databaseUserPassword = rootConfig.getString("mongo.password")

  private val rootConfig: Config = ConfigFactory.load()

  LoggerFactory.getLogger("org.mongodb.driver").asInstanceOf[Logger].setLevel(Level.DEBUG)
  LoggerFactory.getLogger("org.mongodb.driver.protocol").asInstanceOf[Logger].setLevel(Level.OFF)
  LoggerFactory.getLogger("org.mongodb.driver.cluster").asInstanceOf[Logger].setLevel(Level.OFF)

}