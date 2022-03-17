
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "autolink",
    organization := "net.cloudcenrik",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
      "org.postgresql" % "postgresql" % "42.2.18",
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
      "org.mongodb" % "bson" % "2.3",
      "ch.rasc" % "bsoncodec" % "1.0.1",
      "io.github.ismailfakir" % "scala-common-lib_2.13" % "0.1.7"
    ),
    resolvers +=
      "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

// (optional) If you need scalapb/scalapb.proto or anything from
// google/protobuf/*.proto
libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
)

libraryDependencies += "com.thesamet.scalapb" %% "scalapb-json4s" % "0.11.1"

/* sbt task to startup mongodb*/
lazy val rundb = taskKey[Unit]("Execute the shell script")
rundb := {
  import scala.sys.process._
  Seq("bash", "-c","./startdb.sh") !
}

// libraryDependencies += guice
// libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "net.cloudcenrik.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "net.cloudcenrik.binders._"
// https://mvnrepository.com/artifact/org.reactivemongo/reactivemongo
// libraryDependencies += "org.reactivemongo" %% "reactivemongo" % "1.0.1" % "provided"
// https://mvnrepository.com/artifact/org.reactivemongo/play2-reactivemongo
// libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "1.0.1-play28"