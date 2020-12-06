name := "autolink"
organization := "net.cloudcenrik"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.6.1-P28-B4",
  "org.webjars.npm" % "bootstrap" % "4.3.1",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "net.cloudcenrik.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "net.cloudcenrik.binders._"
