class xlsxContext(){
outer =>
val dir =
val member = 




class XlsxFile(file: File, docType: String, sheetName: String, contentToWrite: Array[Row]) {

  /** auxiliary constructors */
  def this(dqaFile){
    val sheetName = ... construct sheeet name
    this(dqaFile.file, dqaFile.docType, sheetName, dqaFile.content)
  }

  override def toString: String = {
    return ("%s opis %s opis %d cyfra).format(dataType, cos, 1)
  }

}



}
