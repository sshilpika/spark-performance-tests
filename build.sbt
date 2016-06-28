name := "spark-performance-tests"

version := "1.0"

scalaVersion := "2.10.6"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

mainClass in assembly := Some("MemTimeCheck")

resolvers ++= Seq(
  "gkthiruvathukal@bintray" at "http://dl.bintray.com/gkthiruvathukal/maven",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= Seq(

  "org.apache.spark" % "spark-sql_2.10" % "1.6.1" % "provided",
  "org.apache.spark" % "spark-core_2.10" % "1.6.1" % "provided",
  "com.github.scopt" %% "scopt" % "3.5.0",
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "com.squants" % "squants_2.10" % "0.6.2",
  "com.databricks" % "spark-csv_2.10" % "1.4.0"
)

