package db

import com.mongodb.event._

class DbQueryCommandListener extends CommandListener {
  override def commandStarted(event: CommandStartedEvent): Unit = {
    println(s"""Sent command '${event.getCommandName}:${event.getCommand.get(event.getCommandName)}'
               | with id ${event.getRequestId} to database '${event.getDatabaseName}'
               | on connection '${event.getConnectionDescription.getConnectionId}' to server
               | '${event.getConnectionDescription.getServerAddress}'""".stripMargin)
  }

  override def commandSucceeded(event: CommandSucceededEvent): Unit = {
    println(s"""Successfully executed command '${event.getCommandName}}'
               | with id ${event.getRequestId}
               | on connection '${event.getConnectionDescription.getConnectionId}' to server
               | '${event.getConnectionDescription.getServerAddress}'
               | response: \n ${event.getResponse.toJson}""".stripMargin)
  }

  override def commandFailed(event: CommandFailedEvent): Unit = {
    println(s"""Failed execution of command '${event.getCommandName}}'
               | with id ${event.getRequestId}
               | on connection '${event.getConnectionDescription.getConnectionId}' to server
               | '${event.getConnectionDescription.getServerAddress}
               | with exception '${event.getThrowable}'""".stripMargin)
  }
}
