name := "2022-PFSD-Proyecto"

version := "0.1"

scalaVersion := "2.13.6"

idePackagePrefix := Some("eci.edu.co")

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.8"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.0.0"
libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "3.0.0"
libraryDependencies += "org.apache.commons" % "commons-dbcp2" % "2.9.0"
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.12.346"
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.4.0-b180830.0359"


  Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

// (optional) If you need scalapb/scalapb.proto or anything from
// google/protobuf/*.proto
libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
)

Test / fork := true