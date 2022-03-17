package db

import java.time.ZonedDateTime

import com.mongodb.MongoCredential.createCredential
import com.mongodb.{MongoCredential, ServerAddress}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoClientSettings

import scala.jdk.CollectionConverters._
import org.bson.codecs.configuration.CodecRegistries
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import db.DbQueryCommandListener
import models.record.{Employee, User, Connection}
import com.mongodb.connection.ServerSettings


object MongoDb extends MongodbConfig {

  private val credential: MongoCredential = createCredential(databaseUser, "admin", databaseUserPassword.toCharArray)

  private val registry: CodecRegistry = CodecRegistries.fromProviders(classOf[Employee],classOf[User], classOf[Connection])

  val codecRegistry: CodecRegistry =
    CodecRegistries.fromRegistries(registry, MongoClient.DEFAULT_CODEC_REGISTRY)

  val settings: MongoClientSettings = MongoClientSettings.builder()
    .applyToClusterSettings(b =>b.hosts(List(new ServerAddress(url)).asJava))
    .codecRegistry(codecRegistry)
    //.addCommandListener(new DbQueryCommandListener)
    .credential(credential)
    .build()

  val client: MongoClient = MongoClient(settings)

  val database: MongoDatabase = client.getDatabase(databaseName)

  val employees: MongoCollection[Employee] = database.getCollection("employee")

  val users: MongoCollection[User] = database.getCollection("user")
  val connections: MongoCollection[Connection] = database.getCollection("connection")

}