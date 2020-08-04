import Dependencies._

ThisBuild / scalaVersion     := "2.12.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.rk"
ThisBuild / organizationName := "rk"

updateOptions := updateOptions.value.withCachedResolution(true)

lazy val root = (project in file("."))
  .settings(
    name := "airbnb-sc-spark",
    libraryDependencies += scalaTest % Test,
	libraryDependencies += sparkCore,
	libraryDependencies += sparkSql,
	libraryDependencies += sparkStreaming,
	libraryDependencies += typesafeConfig,
	libraryDependencies += postgresqlDriver,
	libraryDependencies += scalactic
  )

  
 javaOptions in Test += s"-Dconfig.file=${sourceDirectory.value}/test/resources/application.test.conf";
 fork in Test := true

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
