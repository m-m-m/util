/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.pdf;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.util.context.api.MutableGenericContext;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for PDF
 * documents (content with the mimetype "application/pdf").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Singleton
@Named
public class ContentParserPdf extends AbstractContentParser {

  /** The mimetype. */
  public static final String KEY_MIMETYPE = "application/pdf";

  /** The default extension. */
  public static final String KEY_EXTENSION = "pdf";

  /**
   * The constructor.
   */
  public ContentParserPdf() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getExtension() {

    return KEY_EXTENSION;
  }

  /**
   * {@inheritDoc}
   */
  public String getMimetype() {

    return KEY_MIMETYPE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

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
        context.setVariable(VARIABLE_NAME_TITLE, title);
      }
      String keywords = info.getKeywords();
      if (keywords != null) {
        context.setVariable(VARIABLE_NAME_KEYWORDS, keywords);
      }
      String author = info.getAuthor();
      if (author != null) {
        context.setVariable(VARIABLE_NAME_CREATOR, author);
      }

      if (filesize < options.getMaximumBufferSize()) {
        PDFTextStripper stripper = new PDFTextStripper();
        context.setVariable(VARIABLE_NAME_TEXT, stripper.getText(pdfDoc));
      }
    } finally {
      pdfDoc.close();
    }
  }

}
