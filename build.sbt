name := "file_combinerr"

version := "1.0"

scalaVersion := "2.12.1"

// artifacts versions
val sparkVersion = "2.4.4"
val apachePoiVersion = "3.15-beta2"


// libraries
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.poi" % "poi" % apachePoiVersion,
  "org.apache.poi" % "poi-ooxml" % apachePoiVersion,
  "org.apache.poi" % "poi-ooxml-schemas" % apachePoiVersion
)