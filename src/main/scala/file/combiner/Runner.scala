package file.combiner

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf,SparkContext}

import file.combiner.utils.Loggable


/**
*
*/
object Runner extends Loggable {

  /** */
  def main(args: Array[String]): Unit = {
   
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("File_Combiner")
    
    // inicialize SparkContext and sparkSqlContext
    implicit val sc: SparkContext = new SparkContext(sparkConf)
    implicit val sq: SQLContext = new SQAContext(sc)
    
    
    
  }
  
}
