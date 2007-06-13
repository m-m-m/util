/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
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
   * The constructor.
   */
  public ContentParserXls() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String extractText(POIFSFileSystem poiFs, long filesize) throws Exception {

    int maxBufferSize = getMaximumBufferSize();
    int maxCharSize = maxBufferSize / 2;
    InputStream documentInputStream = poiFs.createDocumentInputStream(POIFS_EXCEL_DOC);
    // actually there seems no smart guess for the initial capacity of
    // textBuffer
    // the text length can have any ration to documentInputStream.available()
    // the only possibility would be to create the string buffer in the listener
    // from the size of the SSTRecord. In this case stable code is better than
    // saving a tiny percent of performance...
    StringBuffer textBuffer = new StringBuffer(1024);
    try {
      HSSFRequest req = new HSSFRequest();
      req.addListenerForAllRecords(new ExcelListener(textBuffer, maxCharSize));
      HSSFEventFactory factory = new HSSFEventFactory();
      factory.processEvents(req, documentInputStream);
    } finally {
      documentInputStream.close();
    }
    return textBuffer.toString();
  }

  /**
   * This inner class acts as listener for HSSF events and appends the received
   * text to a string-buffer.
   */
  protected static class ExcelListener implements HSSFListener {

    /** the buffer where to append the text */
    private final StringBuffer buffer;

    /** the maximum capacity */
    private final int bufferLimit;

    /** list with the sheet names */
    private final List<String> sheetNames;

    /** current SST record (table with unique strings) */
    private SSTRecord sstrec;

    /** current row */
    private int row;

    /** current sheet */
    private int sheet;

    /**
     * The constructor.
     * 
     * @param textBuffer is the buffer where to append the text to.
     * @param maximumBufferSize is the maximum allowed size of the
     *        <code>textBuffer</code>.
     */
    public ExcelListener(StringBuffer textBuffer, int maximumBufferSize) {

      super();
      this.buffer = textBuffer;
      this.bufferLimit = maximumBufferSize;
      this.sheetNames = new ArrayList<String>();
      this.sstrec = null;
      this.row = 0;
      this.sheet = 0;
    }

    /**
     * This method appends the given <code>text</code> to the buffer.
     * 
     * @param text is the text to append.
     */
    private void append(String text) {

      this.buffer.append(text);
      this.buffer.append(' ');
    }

    /**
     * {@inheritDoc}
     */
    public void processRecord(Record record) {

      if (this.buffer.length() < this.bufferLimit) {
        switch (record.getSid()) {
          case BOFRecord.sid:
            BOFRecord bof = (BOFRecord) record;
            if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
              if (this.sheet < this.sheetNames.size()) {
                if (this.sheet > 0) {
                  this.buffer.append("\n\n");
                }
                this.buffer.append("== ");
                this.buffer.append(this.sheetNames.get(this.sheet));
                this.buffer.append(" ==\n");
              }
              this.sheet++;
              this.row = 0;
            }
            break;
          case BoundSheetRecord.sid:
            BoundSheetRecord bsr = (BoundSheetRecord) record;
            this.sheetNames.add(bsr.getSheetname());
            break;
          case NumberRecord.sid:
            NumberRecord numrec = (NumberRecord) record;
            append(Double.toString(numrec.getValue()));
            break;
          case SSTRecord.sid:
            this.sstrec = (SSTRecord) record;
            break;
          case LabelSSTRecord.sid:
            if (this.sstrec != null) {
              LabelSSTRecord lrec = (LabelSSTRecord) record;
              int newRow = lrec.getRow();
              if (this.row != newRow) {
                this.buffer.append('\n');
                this.row = newRow;
              }
              append(this.sstrec.getString(lrec.getSSTIndex()).getString());
            }
            break;
        }
      }
    }

  }

}
