name := """seed"""
organization := "xxx"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += jdbc
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.1"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.12"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "xxx.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "xxx.binders._"
