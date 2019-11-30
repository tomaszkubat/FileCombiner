name := "dqa_summary_generator"

version := "1.0"

scalaVersion := "2.10.5"

val sparkVersion = "1.6.3"
val apachePoiVersion = "3.15-beta2"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.poi" % "poi" % apachePoiVersion,
  "org.apache.poi" % "poi-ooxml" % apachePoiVersion,
  "org.apache.poi" % "poi-ooxml-schemas" % apachePoiVersion
)