package file.combiner.logging


import org.apache.log4j.LogManager


trait Logger {
  private val logger = LogManager.getLogger("file.combiner")

  def debug(s: String): Unit = logger.debug(s)
  def info(s: String): Unit = logger.info(s)
  def warn(s: String): Unit = logger.warn(s)
  def error(s: String): Unit = logger.error(s)
}
