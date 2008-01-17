/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;

import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.util.resource.ClasspathResource;
import net.sf.mmm.util.resource.DataResource;

import static org.junit.Assert.*;

/**
 * This is the test-case for {@link AbstractPoiContentParser}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AbstractPoiContentParserTest {

  /**
   * The constructor.
   */
  public AbstractPoiContentParserTest() {

    super();
  }

  protected Properties parse(ContentParser parser, String resourceName) throws Exception {

    DataResource testResource = new ClasspathResource(AbstractPoiContentParserTest.class
        .getPackage(), resourceName);
    InputStream inputStream = testResource.openStream();
    try {
      return parser.parse(inputStream, testResource.getSize());
    } finally {
      inputStream.close();
    }
  }

  public void checkPpt(ContentParser parser) throws Exception {

    Properties properties = parse(parser, "test.ppt");
    String title = properties.getProperty(ContentParser.PROPERTY_KEY_TITLE);
    assertEquals("Title of Testslide", title);
    String author = properties.getProperty(ContentParser.PROPERTY_KEY_AUTHOR);
    assertEquals("J\u00F6rg Hohwiller", author);
    String keywords = properties.getProperty(ContentParser.PROPERTY_KEY_KEYWORDS);
    assertEquals("some keywords", keywords);
  }

  @Test
  public void testParser() throws Exception {

    AbstractPoiContentParser parser = new AbstractPoiContentParser() {

      protected String extractText(POIFSFileSystem poiFs, long filesize) throws Exception {

        return "";
      }

    };
    checkPpt(parser);
    // TODO: word and excel...
  }

}
