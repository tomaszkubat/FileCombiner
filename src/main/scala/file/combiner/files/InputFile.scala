package file.combiner.files

import java.io.{File => jFile}
import org.apache.spark.sql.{DataFrame, Row, SQLContext}
import file.combiner.files._

/**
  * This class is a handy 'wrapper' to get/set useful information about the file for further operations.
  *
  * @param iFile file to create a InputFile
  * @param orgUnit org unit
  * @param SQLContext Spark sql context
  * @author tomaszk
  */
class InputFile (override val iFile: jFile, val orgUnit: String, SQLContext: SQLContext)
  extends file.combiner.files.File (iFile: jFile) {

  /** Decompose file name into smaller variables. Input file naming convention:
    * - structure: "[setName]_[timestamp].csv"
    * - example: "budget_201903.csv"
    *
    * File name decomposition pattern rules:
    * ([^_]+)_           - a group of any characters with exception of underscore, one underscore in the end
    * \\.                - escaped dot
    * (.+)               - a string of one ore more character considered as a group
    */

  // pattern for typical output file name
  private val fileNameDecomposePattern = "([^_]+)_([^_]+)\\.(.+)".r

  // decompose file name into smaller pieces
  val (setName: String, timestamp: String) = fileName match {
    case fileNameDecomposePattern(set,tmp,_) => (set,tmp)
    case _ => throw new Exception(s"Input file name $fileName doesn't satisfy the pattern: $fileNameDecomposePattern")
  }

  // get file content
  private val contentdf: DataFrame = SQLContext.read
    .format("com.databricks.spark.csv")
    .option("header","true")
    .load(iFile.getAbsoluteFile().toString) // DataFrame

  // convert content to the Array of Row
  val fileContent: Array[Row] = contentdf
    .na.fill(".", contentdf.columns.toSeq) // null handling
    .collect()

  // count rows/columns - solve the situation of empty input files
  val (fileContentRowNum: Int,fileContentColNum: Int) = {
    val c = fileContent
    if (!c.isEmpty)(c.length,c.head.length) else (0,0)
  }

}