/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.junit.Test;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.impl.java.ContentParserJava;

/**
 * This is the {@link TestCase} for {@link ContentParserJava}.
 * 
 * @author <a href="mailto:hohwille@users.sourceforge.net">Jörg Hohwiller</a>
 * @author Nobody Else
 */
@SuppressWarnings("all")
public class ContentParserJavaTest {

  private Properties parse(ContentParser parser, String basePath, Class clazz) throws Exception {

    String classPath = clazz.getName().replace('.', '/');
    File sourceFile = new File(basePath + "/" + classPath + ".java");
    InputStream inputStream = new FileInputStream(sourceFile);
    try {
      Properties metadata = parser.parse(inputStream, sourceFile.length());
      assertEquals(clazz.getName(), metadata.getProperty(ContentParser.PROPERTY_KEY_TITLE));
      return metadata;
    } finally {
      inputStream.close();
    }
  }

  @Test
  public void testJavaParser() throws Exception {

    ContentParserJava parser = new ContentParserJava();
    parser.initialize();
    Properties metadata = parse(parser, "src/test/java/", ContentParserJavaTest.class);
    assertEquals("Jörg Hohwiller, Nobody Else", metadata
        .getProperty(ContentParser.PROPERTY_KEY_AUTHOR));
    metadata = parse(parser, "src/main/java/", ContentParserJava.class);
    assertEquals("Joerg Hohwiller", metadata.getProperty(ContentParser.PROPERTY_KEY_AUTHOR));
  }

}
