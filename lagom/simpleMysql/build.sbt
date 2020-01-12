
organization in ThisBuild := "test.erp"
name := """test-erp"""
version in ThisBuild := "1.0.0-RC1"

scalaVersion in ThisBuild := "2.12.7"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val mySql = "mysql" % "mysql-connector-java" % "8.0.12"


lazy val `product-amount-api` = (project in file("product-amount-api"))
  .settings(apiCommonSettings)

lazy val `product-amount-impl` = (project in file("product-amount-impl"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPubSub,
      macwire,
      lagomScaladslApi ,
      lagomScaladslPersistenceJdbc,
      mySql
    )
  )
  .dependsOn(`product-amount-api`)


lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false
lagomServiceLocatorEnabled in ThisBuild := false

val apiCommonSettings = libraryDependencies ++= Seq(
  lagomScaladslApi
)
