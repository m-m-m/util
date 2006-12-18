/* $Id$ */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the implementation of the {@link ContentParser} interface for PDF
 * documents (content with the mimetype "application/pdf").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPoiContentParser implements ContentParser {

  /** name of the entry for a word document in the POI filesystem */
  public static final String POIFS_WORD_DOC = "WordDocument";

  /** name of the entry for a powerpoint document in the POI filesystem */
  public static final String POIFS_POWERPOINT_DOC = "PowerPoint Document";

  /**
   * The constructor
   */
  public AbstractPoiContentParser() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.api.ContentParser#parse(java.io.InputStream,
   *      java.lang.String)
   */
  public Properties parse(InputStream inputStream, String filename) throws Exception {

    Properties properties = new Properties();
    POIFSFileSystem poiFs = new POIFSFileSystem(inputStream);
    SummaryInformation summaryInfo = (SummaryInformation) PropertySetFactory.create(poiFs
        .createDocumentInputStream(SummaryInformation.DEFAULT_STREAM_NAME));
    String title = summaryInfo.getTitle();
    if (title == null) {
      if (filename != null) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot > 0) {
          title = filename.substring(0, lastDot);
        } else {
          title = filename;
        }
      }
    }
    if (title != null) {
      properties.setProperty(PROPERTY_KEY_TITLE, title);
    }
    String author = summaryInfo.getAuthor();
    if (author != null) {
      properties.setProperty(PROPERTY_KEY_AUTHOR, author);
    }
    String keywords = summaryInfo.getKeywords();
    if (keywords != null) {
      properties.setProperty(PROPERTY_KEY_KEYWORDS, keywords);
    }
    properties.setProperty(PROPERTY_KEY_TEXT, extractText(poiFs));
    return properties;
  }

  /**
   * This method extracts the text from the office document given by
   * <code>poiFs</code>.
   * 
   * @param poiFs is the POI filesystem of the office document.
   * @return the plain text extracted from the content.
   * @throws Exception if something goes wrong.
   */
  protected abstract String extractText(POIFSFileSystem poiFs) throws Exception;

}
