/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.FileInputStream;

import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.textmining.text.extraction.WordExtractor;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for PDF
 * documents (content with the mimetype "application/pdf").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserDoc extends AbstractPoiContentParser {

  /**
   * The constructor
   */
  public ContentParserDoc() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.impl.AbstractPoiContentParser#extractText(org.apache.poi.poifs.filesystem.POIFSFileSystem, long)
   */
  @Override
  protected String extractText(POIFSFileSystem poiFs, long filesize) throws Exception {

    // DocumentEntry documentEntry = (DocumentEntry)
    // poiFs.getRoot().getEntry(POIFS_WORD_DOC);
    // DocumentInputStream documentInputStream =
    // poiFs.createDocumentInputStream(POIFS_ENTRY);
    WordExtractor extractor = new WordExtractor();
    return extractor.extractText(poiFs);
  }

  public static void main(String[] args) throws Exception {

    System.out.println(new ContentParserDoc().parse(
        new FileInputStream("C:\\tmp\\Sicherheitskonzept.doc"), -1)
        .getProperty(PROPERTY_KEY_TEXT));
  }

}
