name := """excel"""
organization := "xxx"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.apache.poi" % "poi" % "4.0.1"
libraryDependencies += "org.apache.poi" % "poi-ooxml" % "4.0.1"
libraryDependencies +=  "com.opencsv" % "opencsv" % "4.2"

libraryDependencies += "org.jxls" % "jxls" % "2.4.3" exclude ("org.slf4j", "slf4j-simple")
libraryDependencies += "org.jxls" % "jxls-poi" % "1.0.14" exclude ("org.slf4j", "slf4j-simple")


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "xxx.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "xxx.binders._"
