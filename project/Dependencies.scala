import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1"
  lazy val sparkCore = "org.apache.spark" %% "spark-core" % "2.4.6"
  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % "2.4.6"
  lazy val sparkStreaming = "org.apache.spark" %% "spark-streaming" % "2.4.6"
  lazy val typesafeConfig = "com.typesafe" % "config" % "1.4.0"
  lazy val postgresqlDriver = "org.postgresql" % "postgresql" % "9.4.1212"
  lazy val scalactic = "org.scalactic" %% "scalactic" % "3.1.1"
}
