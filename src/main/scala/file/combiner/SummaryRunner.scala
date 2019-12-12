package file.combiner

import java.io.{File => jFile} // rename File to jFile
import org.apache.spark.sql.{SQLContext,DataFrame}
import file.combiner.utils.ConfigLoader
import file.combiner.utils.DirChecker
import file.combiner.files._

class SummaryRunner(sqlContext: SQLContext, orgUnit: String, config: ConfigLoader) {

  /** pre-operations */
    DirChecker
      .createDirectory(new jFile(config.outputPath + "/" + "orgUnit")) // create new directory


  /** load input data */
    val files = DirChecker
      .getFileList(new jFile(config.inputPath + "/" + "orgUnit"))// load data files


}
