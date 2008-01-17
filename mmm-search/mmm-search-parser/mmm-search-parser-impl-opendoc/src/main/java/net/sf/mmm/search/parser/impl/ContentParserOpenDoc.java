/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.search.parser.base.AbstractContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for content of
 * the open-document format (content with the mimetypes
 * "application/vnd.oasis.opendocument.*").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserOpenDoc extends AbstractContentParser {

  /** the content.xml entry of the document */
  private static final String ENTRY_CONTENT_XML = "content.xml";

  /** the meta.xml entry of the document */
  private static final String ENTRY_META_XML = "meta.xml";

  /**
   * The constructor.
   */
  public ContentParserOpenDoc() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void parse(InputStream inputStream, long filesize, String encoding, Properties properties)
      throws Exception {

    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
    // new BufferedInputStream(zipInputStream);
    ZipEntry zipEntry = zipInputStream.getNextEntry();
    while (zipEntry != null) {
      if (zipEntry.getName().equals(ENTRY_CONTENT_XML)) {
        XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(
            zipInputStream);
        streamReader.getLocalName();
      } else if (zipEntry.getName().equals(ENTRY_META_XML)) {
        XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(
            zipInputStream);
        // TODO: ...
      }
      zipInputStream.closeEntry();
      zipEntry = zipInputStream.getNextEntry();
    }
    zipInputStream.close();
  }

}
