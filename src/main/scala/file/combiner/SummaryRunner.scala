package file.combiner

import java.io.{File => jFile} // rename File to jFile
import org.apache.spark.sql.{SQLContext,DataFrame}
import file.combiner.utils.{ConfigLoader,DirChecker}
import file.combiner.logging.Logger
import file.combiner.files._

class SummaryRunner(sqlContext: SQLContext, orgUnit: String, config: ConfigLoader)
  extends Logger {


  /** pre-operations */
    val inpDir = new jFile(config.inputPath + "/" + orgUnit)
    val outDir = new jFile(config.outputPath + "/" + orgUnit)

    DirChecker.createDirectory(outDir) // create new directory

  /** load input data */
    // load input files
    val inputFiles: List[InputFile] = DirChecker
      .getFileList(inpDir)// load data files
      .map(new InputFile(_, orgUnit, sqlContext)) // convert jFale to InputFile
      .filter(!_.fileContent.isEmpty) // remove empty files

    // get unique list of data set types
    val setsTypes: List[String] = inputFiles
      .map(_.setName)
      .distinct
      .sorted


  /** generating summary */

    info(s"Start processing ${orgUnit}")

    setsTypes.foreach(setType =>

      info(s"Start processing ${orgUnit} data set ${setType}")


    )



}
