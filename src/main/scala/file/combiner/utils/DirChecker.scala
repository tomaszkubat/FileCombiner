package file.combiner.utils

import java.io.{File}
import file.combiner.logging.Logger


/**
  * Object to make basic operations on a directories:
  * - creating directories,
  * - listing files inside directory,
  * - generate path to the directory dynamically.
  * @author tomaszk
  */
object DirChecker extends Logger {

  /**
    * Check if provided path - if it's empty, create the path dynamically base on the worksapce and sub directory strings
    * Create the subdirectories, if neccessary.
    * @param pathToCheck path to check
    * @param propertyPath path to the property file
    * @param workspaceString name of the workspace to traverse up
    * @param subdirString name of subdirectory to traverse down
    * @return
    */
  def generatePathDynamically(pathToCheck: String, propertyPath: String, workspaceString: String, subdirString: String): String = {

    /**
      * Traverse the directory up and try to find the given dir.
      * @param dir directory to start traversing
      * @param dirToFind directory name to find
      * @return master directory
      */
    def traverseDirUp(dir: java.io.File, dirToFind: String): java.io.File = {

      var d = dir // create mutable variables

      try {// traverse the dir up and try to find the given end directory
        do {d = new java.io.File(d.getParent())}
        while (d.getName != dirToFind) // condition to continue loop
      } catch {// throw an exception when the provided directory can not be found
        case e:NullPointerException => throw new Exception(s"The directory '$dirToFind' can not be found in the given path '${d.getAbsolutePath}'")
      }

      d
    }

    // check the path and generate it dynamically, if required
    val pathChecked = {
      if (pathToCheck.isEmpty){
        val workspace = traverseDirUp(new File(propertyPath), workspaceString)
        workspace.getAbsolutePath + "/" + subdirString
      } else pathToCheck
    }

    // create subdirectories if required
    createDirectory(new File(pathChecked))

    pathChecked

  }


  /**
    * Create a new directory, if not exist
    * @param dir directory to create
    */
  def createDirectory(dir: File): Unit = {
    if (!dir.exists()){
      info(s"Creating new directory ${dir.getAbsolutePath}")
      dir.mkdirs()
    } else {
      info(s"Directory already exists ${dir.getAbsolutePath}")
    }
  }


  /**
    * Get list of files in provided directory.
    * @return list of files
    */
  def getFileList(dir: File): List[File] = {
    if (dir.exists()) {
      info(s"Getting files from directory ${dir.getAbsolutePath}")
      dir
        .listFiles // get list of files [File] in given directory
        .filter(_.isFile()) // get only files
        .toList
    } else {
      Nil // return empty list
    }
  }

}