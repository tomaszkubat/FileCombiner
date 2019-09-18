package file.combiner.utils

import org.apache.log4j.LogManager


trait Loggable {
  
  /** subsidiary object */
  private val logger = LogManager.getLogger("file.combiner.ScalaFileCombiner")
  
  /** methods to debug the code */
  def error(s: String): Unit = logger.error(s)
  def warn(s: String): Unit = logger.warn(s)
  def info(s: String): Unit = logger.info(s)
  def debug(s: String): Unit = logger.debug(s)
  
}
