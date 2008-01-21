/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;

import net.sf.mmm.search.parser.base.AbstractContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for PDF
 * documents (content with the mimetype "application/pdf").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserPdf extends AbstractContentParser {

  /**
   * The constructor.
   */
  public ContentParserPdf() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void parse(InputStream inputStream, long filesize, String encoding, Properties properties)
      throws Exception {

    PDFParser parser = new PDFParser(inputStream);
    parser.parse();
    PDDocument pdfDoc = parser.getPDDocument();
    try {
      if (pdfDoc.isEncrypted()) {
        // pdfDoc.decrypt("password");
        return;
      }
      PDDocumentInformation info = pdfDoc.getDocumentInformation();
      String title = info.getTitle();
      if (title != null) {
        properties.setProperty(PROPERTY_KEY_TITLE, title);
      }
      String keywords = info.getKeywords();
      if (keywords != null) {
        properties.setProperty(PROPERTY_KEY_KEYWORDS, keywords);
      }
      String author = info.getAuthor();
      if (author != null) {
        properties.setProperty(PROPERTY_KEY_AUTHOR, author);
      }

      if (filesize < getMaximumBufferSize()) {
        PDFTextStripper stripper = new PDFTextStripper();
        properties.setProperty(PROPERTY_KEY_TEXT, stripper.getText(pdfDoc));
      }
    } finally {
      pdfDoc.close();
    }
  }

}
