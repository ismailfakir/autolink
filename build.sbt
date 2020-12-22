
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
      "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
    )
  )



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


