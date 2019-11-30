package file.combiner

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf,SparkContext}
import com.typesafe.config.ConfigFactory

import file.combiner.utils.Loggable


/**
*
*/
object Runner {

  /**
    *
    */
  def main(args: Array[String]): Unit = {

    /*
    val propertyPath = if(args.isEmpty) "src/main/resources/init.properties" else args(0)
    
    val config = ConfigFactory.parseFile(new File(propertyPath))
    
    val a = config.getString("rawDataPath")
    
    
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("File_Combiner")
    
    // inicialize SparkContext and sparkSqlContext
    implicit val sc: SparkContext = new SparkContext(sparkConf)
    implicit val sq: SQLContext = new SQAContext(sc)
    
    */
  }
  
}
