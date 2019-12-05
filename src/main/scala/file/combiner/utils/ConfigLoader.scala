package file.combiner.utils

import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import com.typesafe.config.ConfigFactory


/**
  * Import and store properties based on provided "configuration.properties" file
  * @param propertyPathString string to the property file
  * @author tomaszk
  */
class ConfigLoader(propertyPathString: String) {


  private var configFile = new File(propertyPathString)
  private var config = ConfigFactory.parseFile(configFile)


  /** "dqa.properties" file based parameters */
  private val workspaceString = config.getString("workspacePath")
  val inputPath = workspaceString + "/" + config.getString("inputPath")
  val outputPath = workspaceString + "/" + config.getString("outputPath")

  val orgUnits = config.getString("orgUnitsToProcess").split(",").toList


  /** additional parameters */

  // current timestamp
  private var myFormat = new SimpleDateFormat("yyyyMMdd_HHmmss")
  val timestamp = myFormat.format(Calendar.getInstance().getTime())

}