package file.combiner.files

import java.io.{FileInputStream, FileOutputStream}
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel
import org.apache.poi.ss.usermodel.Sheet
import org.apache.spark.sql.Row
import file.combiner.logging.Logger


/**
  * Class to perform writing the dqa to the xls file
  * @param summaryDir directory to create xls files
  * @author tomaszk
  * @since 2019.09.18
  */
class XlsContext(summaryDir: java.io.File) extends Logger {

  outer => // alias to use in the inner class

  val summaryPath = summaryDir.getAbsolutePath


  /**
    * Method to construct an XlsFile instance
    * @param xlsFileName xls file name (without extension)
    * @param orgUnit org Unit
    * @author tomasz.kubat
    */
  def createXlsFile(xlsFileName: String, orgUnit: String): XlsFile = {

    // check if the file name was specified
    if (xlsFileName.length > 0) {
      debug(s"Creating XlsFile class instance ${xlsFileName}")
      new XlsFile(new java.io.File(summaryPath + "/" + xlsFileName + ".xls"), orgUnit)
    } else {
      throw new Exception("Provided fileName is empty, should contains at least one character")
    }

  }


  /**
    * Handy class to create xls components and perform further actions
    * Apache POI tutorial: https://poi.apache.org/components/poi-jvm-languages.html
    * @param orgUnit - DqaFile to write to xls
    */
  class XlsFile(file: java.io.File, orgUnit: String)
    extends File (file: java.io.File) {


    /**
      * Open workbook - use existing file or create a new one before.
      * @return HSSFWorkbook
      */
    def openWorkbook(): HSSFWorkbook = {

      if (this.file.exists()) {
        debug(s"|--Creating workbook for an existing file")
        val fileInput = new FileInputStream(this.file) // create input stream
        new HSSFWorkbook(fileInput) // open existing workbook
      } else {
        debug(s"|--Creating workbook for a new file")
        new HSSFWorkbook() // open a new workbook
      }

    }


    /**
      * Close workbook - remember to close a worksheet after all performed operations!
      * @param workbook
      */
    def closeWorkbook(workbook: HSSFWorkbook): Unit = {

      debug(s"|--Saving workbook")

      val fileOutput = new FileOutputStream(this.file) // create output stream

      workbook.setActiveSheet(0) // activate the first sheet
      workbook.write(fileOutput) // write content to a workbook
      workbook.close()

      debug(s"|--Workbook closed")

    }


    /**
      * Write content to the sheet and perform or required operations on sheet.
      * @param workbook workbook to write a sheet
      * @param sheetName sheet to use
      * @param content content to write (Array[sql.Row])
      */
    def writeToSheet(workbook: HSSFWorkbook, sheetName: String, content: Array[Row]): Unit = {


      /**
        * Check if sheet exists in the workbook
        * @param workbook
        * @param sheetName
        * @return
        */
      def sheetExist(workbook: HSSFWorkbook, sheetName: String): Boolean = {

        val totalSheetCount = workbook.getNumberOfSheets() // count the number of sheets in the workbook

        if (totalSheetCount == 0) {false} else {

          var cnt: Int = 0 // technical counter to check, if provided sheet exists

          // iterate sheets
          val sheetIterator = workbook.iterator() // create sheet iterator
          while (sheetIterator.hasNext()) {
            val currentSheet: Sheet = sheetIterator.next()
            if (currentSheet.getSheetName().equals(sheetName)) cnt = cnt + 0 else cnt = cnt + 1
          }

          if (totalSheetCount == cnt) false else true // if totalSheetCount = cnt it means, that your file had not been found
        }
      }


      /**
        * Iterate through the sheet to save the desired content.
        * @param sheet sheet to write a cntent
        * @param contentToWrite content to write (Array[sql.Row])
        */
      def iterateSheet(sheet: Sheet, contentToWrite: Array[Row]): Unit = {

        debug(s"|--iterating sheet")

        // proceed writing content to the sheet
        contentToWrite.foreach{elem =>

          val row = activateNextRow(sheet) // get next row

          for (colInd <- 0 until elem.length) {
            val cellValue: String = elem(colInd).toString // Option().getOrElse("").toString
            val cell = row.createCell(colInd) // create new cell
            cell.setAsActiveCell()
            cell.setCellValue(cellValue) // write to a cell
          }

        }


        /**
          * Active new row in a given sheet
          * @param sheet sheet
          * @return usermodel.Row
          */
        def activateNextRow(sheet: Sheet): usermodel.Row = {
          val lr = sheet.getPhysicalNumberOfRows() // find last row
          sheet.createRow(lr)
        }

      }


      debug(s"|--Processing workbook")

      // check if the dqa file content is not empty
      if (!content.isEmpty) {

        val sName: String = if (sheetName.length > 30) sheetName.substring(0, 30) else sheetName // cut sheet name max to 30 characters

        if (!sheetExist(workbook, sName)) workbook.createSheet(sName) // create sheet if doesn't exist

        val sIndex: Int = workbook.getSheetIndex(sName)// get sheet index
        val sheet = workbook.getSheet(sName) // create sheet object

        workbook.setActiveSheet(sIndex) // set sheet as active
        iterateSheet(sheet, content)    // write content to the sheet

      } else {
        debug(s"|--Content is empty, nothing to write")
      }

    }

  }

}