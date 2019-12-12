package file.combiner

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import java.io.File
import file.combiner.logging.Logger


/**
* @author tomasz.kubat
*/
object Runner extends Logger {

  /**
    * Main
    * @param first param is a path to the property file
    */
  def main(args: Array[String]): Unit = {

    warn(s"Starting application")

    /** load configuration */
    val propertyPath = if(!args.isEmpty) args(0) else "src/main/resources/configuration.properties"

    val config = ConfigFactory.parseFile(new File(propertyPath))
    val a = config.getString("rawDataPath")


    /** inicialize Spark */
    val sparkConf = new SparkConf().setMaster("local").setAppName("File_Combiner")
    val sc: SparkContext = new SparkContext(sparkConf)
    val sq: SQLContext = new SQLContext(sc)





    warn(s"Application finished")

  }
  
}
