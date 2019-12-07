package file.combiner.files

import org.apache.spark.sql.{DataFrame, Row, SQLContext}


/**
  * This class is a handy 'wrapper' to get/set useful information about the file for further operations.
  *
  * @param file file to create a InputFile
  * @param orgUnit org unit
  * @param SQLContext Spark sql context
  * @author tomaszk
  * @since 2019.09.18
  */
class InputFile (override val file: java.io.File, override val orgUnit: String, SQLContext: SQLContext)
  extends file.combiner.files.File (file: java.io.File, orgUnit: String) {

  /** Decompose file name into smaller variables.
    *
    * Input file naming convention:
    * - structure: "[setName]_[testType]_[fieldName]_[timestamp].csv"
    * - example: "claim-party-address_length_all_20190104_010000.csv"
    *
    * File name decomposition pattern rules:
    * ([^_]+)_           - a group of any characters with exception of underscore, one underscore in the end
    * \\.                - escaped dot
    * (.+)               - a string of one ore more character considered as a group
    */

  // pattern for typical output file name
  private val fileNameDecomposePattern = "([^_]+)_([^_]+)_([^_]+)_(\\d{8})_(\\d{4})\\.(.+)".r

  // decompose file name into smaller pieces
  val (setName: String, testName: String, colName: String) = fileName match {
    case fileNameDecomposePattern(set,test,col,date,time,_) => (set,test,col)
    case _ => throw new Exception(s"Input file name $fileName doesn't satisfy the pattern: $fileNameDecomposePattern")
  }

  // get document type [String]
  val docType: String = setName.split("-").toList  match {
    case x :: xs => x // get fist element if list is not empty
    case _ => throw new Exception(s"Doc type not recognized for a file $fileName")
  }

  // get file content
  private val contentdf: DataFrame = SQLContext.read
    .format("com.databricks.spark.csv")
    .option("header","false")
    .load(file.getAbsoluteFile().toString) // DataFrame

  // convert content to the Array of Row
  val fileContent: Array[Row] = contentdf
    .na.fill(".", contentdf.columns.toSeq) // null handling
    .collect()

  // it's possible that some flag files has no content - solve this issue
  val (fileContentRowNum: Int,fileContentColNum: Int) = {
    val c = fileContent
    if (!c.isEmpty)(c.length,c.head.length) else (0,0)
  }

}