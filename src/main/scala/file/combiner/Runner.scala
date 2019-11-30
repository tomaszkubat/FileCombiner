package file.combiner

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf,SparkContext}
// import com.typesafe.config.ConfigFactory

import java.io.File

import file.combiner.logging.Logger


/**
*
*/
object Runner extends Logger {

  /**
    *
    */
  def main(args: Array[String]): Unit = {

    warn(s"Starting application")

//    val propertyPath = if(!args.isEmpty) args(0) else "src/main/resources/configuration.properties"
//
//
//    val config = ConfigFactory.parseFile(new File(propertyPath))
//    val a = config.getString("rawDataPath")
//
//
//    val sparkConf = new SparkConf()
//      .setMaster("local")
//      .setAppName("File_Combiner")
//
//    // inicialize SparkContext and sparkSqlContext
//    implicit val sc: SparkContext = new SparkContext(sparkConf)
//    implicit val sq: SQLContext = new SQAContext(sc)
//

  }
  
}
