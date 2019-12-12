package file.combiner.files

/**
  * Small abstract class for a few types of files like DqaFile and XlsFile
  * @param file java.io.File
  * @param orgUnit orgUnit/member [String]
  * @author tomaszk
  * @since 2019.09.18
  */
abstract class File(val iFile: java.io.File) {

  val fileName: String = iFile.getName() // get file name as String
  override def toString(): String = fileName // override toString method to show file name

}