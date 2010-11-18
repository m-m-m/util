/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.poi;

import java.io.InputStream;

import net.sf.mmm.content.parser.api.ContentParserOptions;
import net.sf.mmm.content.parser.base.AbstractContentParser;
import net.sf.mmm.util.context.api.MutableGenericContext;

import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.content.parser.api.ContentParser} interface for parsing
 * binary Microsoft office documents using apache POI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractContentParserOoxml extends AbstractContentParser {

  /** The generic mimetype for OOXML. */
  public static final String KEY_MIMETYPE_GENERIC = "application/vnd.openxmlformats";

  /**
   * The constructor.
   */
  public AbstractContentParserOoxml() {

    super();
  }

  /**
   * This method creates the {@link POIXMLTextExtractor} for the given
   * <code>opcPackage</code>.
   * 
   * @param opcPackage is the {@link OPCPackage}.
   * @return the {@link POIXMLTextExtractor}.
   * @throws Exception if something goes wrong.
   */
  protected POIXMLTextExtractor createExtractor(OPCPackage opcPackage) throws Exception {

    return ExtractorFactory.createExtractor(opcPackage);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, ContentParserOptions options,
      MutableGenericContext context) throws Exception {

    OPCPackage opcPackage = OPCPackage.open(inputStream);
    POIXMLTextExtractor extractor = createExtractor(opcPackage);
    CoreProperties coreProperties = extractor.getCoreProperties();
    String title = coreProperties.getTitle();
    if (title != null) {
      context.setVariable(VARIABLE_NAME_TITLE, title);
    }
    String author = coreProperties.getCreator();
    if (author != null) {
      context.setVariable(VARIABLE_NAME_CREATOR, author);
    }
    String keywords = coreProperties.getKeywords();
    if (keywords != null) {
      context.setVariable(VARIABLE_NAME_KEYWORDS, keywords);
    }
    context.setVariable(VARIABLE_NAME_TEXT, extractText(extractor, filesize));
  }

  /**
   * This method extracts the text from the office document given by
   * <code>poiFs</code>.
   * 
   * @param extractor is the {@link POIXMLTextExtractor}.
   * @param filesize is the size (content-length) of the content to parse in
   *        bytes or <code>0</code> if NOT available (unknown). If available,
   *        the parser may use this value for optimized allocations.
   * @return the plain text extracted from the content.
   * @throws Exception if something goes wrong.
   */
  protected String extractText(POIXMLTextExtractor extractor, long filesize) throws Exception {

    return extractor.getText();
  }

}
