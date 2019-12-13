package file.combiner

import java.io.{File => jFile} // rename File to jFile
import org.apache.spark.sql.{SQLContext,Row}
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

    info(s"Processing ${orgUnit}")

    // use XlsContext logic to create a new xls file and ensure writting to the xls file
    val xlsContext = new XlsContext(outDir) // create XlsContext instance to perform xls stuff
    val xls = xlsContext.createXlsFile(orgUnit, orgUnit) // create XlsFile instance
    val wb = xls.openWorkbook() // open workbook (create workbook variable)

    // create initial sheet
    xls.writeToSheet(wb, "Files_overview", Array(Row("Set_name", "File_name", "rows", "cols")))


    // proceed for each set type
    inputFiles
      .filter(!_.fileContent.isEmpty) // get only nonempty files
      .foreach(inpFile => {

        info(s"Processing ${orgUnit} file ${inpFile.fileName}")

        xls.writeToSheet(wb, inpFile.setName, inpFile.fileContent)
        xls.writeToSheet(wb, "Files_overview", Array(Row(inpFile.setName, inpFile.fileName, inpFile.fileContentRowNum, inpFile.fileContentColNum)))

    })

    xls.closeWorkbook(wb)


}
