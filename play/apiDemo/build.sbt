name := """seed"""
organization := "xxx"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

// scalaVersion := "2.12.6"

libraryDependencies += guice
// libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.0"
libraryDependencies += jdbc
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.12"
libraryDependencies += ehcache
// libraryDependencies += "com.typesafe.slick" % "slick-codegen_2.10" % "3.0.2"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.1"
libraryDependencies +=  "com.typesafe.slick" %% "slick" % "3.2.3"
libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"




