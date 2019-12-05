name := "file_combinerr"

version := "1.0"

scalaVersion := "2.10.5"

// artifacts versions

val sparkVersion = "1.6.3"
val apachePoiVersion = "3.15-beta2"


// libraries
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "com.databricks" %% "spark-csv" % "1.5.0",
  "org.apache.poi" % "poi" % apachePoiVersion,
  "org.apache.poi" % "poi-ooxml" % apachePoiVersion,
  "org.apache.poi" % "poi-ooxml-schemas" % apachePoiVersion
)