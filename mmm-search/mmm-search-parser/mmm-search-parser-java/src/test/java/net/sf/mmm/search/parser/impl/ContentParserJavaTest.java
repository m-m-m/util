/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import net.sf.mmm.search.parser.api.ContentParser;

import junit.framework.TestCase;

/**
 * This is the
 * 
 * @author <a href="mailto:hohwille@users.sourceforge.net">Jörg Hohwiller</a>
 */
@SuppressWarnings("all")
public class ContentParserJavaTest extends TestCase {

  private Properties parse(ContentParser parser, String basePath, Class clazz) throws Exception {

    String classPath = clazz.getName().replace('.', '/');
    File sourceFile = new File(basePath + "/" + classPath + ".java");
    InputStream inputStream = new FileInputStream(sourceFile);
    try {
      return parser.parse(inputStream, sourceFile.length());
    } finally {
      inputStream.close();
    }
  }

  @Test
  public void testJavaParser() throws Exception {

    ContentParserJava parser = new ContentParserJava();
    Properties metadata = parse(parser, "src/test/java/", ContentParserJavaTest.class);
    assertEquals("Jörg Hohwiller", metadata.getProperty(ContentParser.PROPERTY_KEY_AUTHOR));
  }
  
}
