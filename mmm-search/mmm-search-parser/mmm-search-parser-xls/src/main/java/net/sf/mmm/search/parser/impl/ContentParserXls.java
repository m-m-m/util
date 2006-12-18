/* $Id$ */
package net.sf.mmm.search.parser.impl;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for PDF
 * documents (content with the mimetype "application/pdf").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserXls extends AbstractPoiContentParser {

  /**
   * The constructor
   */
  public ContentParserXls() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.impl.AbstractPoiContentParser#extractText(org.apache.poi.poifs.filesystem.POIFSFileSystem)
   */
  @Override
  protected String extractText(POIFSFileSystem poiFs) throws Exception {

    StringBuffer textBuffer = new StringBuffer(1024);
    HSSFWorkbook parser = new HSSFWorkbook(poiFs);
    // loop over all sheets
    int sheetCount = parser.getNumberOfSheets();
    for (int sheetIndex = 0; sheetIndex < sheetCount; sheetIndex++) {
      textBuffer.append(parser.getSheetName(sheetIndex));
      textBuffer.append(" ");
      HSSFSheet sheet = parser.getSheetAt(sheetIndex);
      // loop over all rows
      int rowMax = sheet.getLastRowNum();
      for (int rowIndex = sheet.getFirstRowNum(); rowIndex <= rowMax; rowIndex++) {
        HSSFRow row = sheet.getRow(rowMax);
        // loop over all cells
        short cellMax = row.getLastCellNum();
        for (short cellIndex = row.getFirstCellNum(); cellIndex <= cellMax; cellIndex++) {
          HSSFCell cell = row.getCell(cellIndex);
          int cellType = cell.getCellType();
          if (cellType == HSSFCell.CELL_TYPE_STRING) {
            textBuffer.append(cell.getStringCellValue().trim());
            textBuffer.append(" ");
          } else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
            textBuffer.append(cell.getNumericCellValue());
            textBuffer.append(" ");
          } // else if (cellType == HSSFCell.CELL_TYPE_FORMULA)
        }
      }
    }
    return textBuffer.toString();
  }

}
