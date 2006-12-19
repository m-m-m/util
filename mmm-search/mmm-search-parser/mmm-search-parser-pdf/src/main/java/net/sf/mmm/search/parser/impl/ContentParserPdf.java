/* $Id$ */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the implementation of the {@link ContentParser} interface for PDF
 * documents (content with the mimetype "application/pdf").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserPdf implements ContentParser {

  /**
   * The constructor
   */
  public ContentParserPdf() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.api.ContentParser#parse(java.io.InputStream,
   *      java.lang.String)
   */
  public Properties parse(InputStream inputStream, String filename) throws Exception {

    Properties properties = new Properties();
    PDFParser parser = new PDFParser(inputStream);
    parser.parse();
    PDDocument pdfDoc = parser.getPDDocument();
    try {
      if (pdfDoc.isEncrypted()) {
        // pdfDoc.decrypt("password");
        return properties;
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

      PDFTextStripper stripper = new PDFTextStripper();
      properties.setProperty(PROPERTY_KEY_TEXT, stripper.getText(pdfDoc));
    } finally {
      pdfDoc.close();
    }
    return properties;
  }

}
