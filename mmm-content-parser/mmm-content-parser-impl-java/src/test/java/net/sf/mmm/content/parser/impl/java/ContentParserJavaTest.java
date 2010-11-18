/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl.java;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.util.context.api.GenericContext;

import org.junit.Test;

/**
 * This is the {@link TestCase} for {@link ContentParserJava}.
 * 
 * @author <a href="mailto:hohwille@users.sourceforge.net">Jörg Hohwiller</a>
 * @author Nobody Else
 */
@SuppressWarnings("all")
public class ContentParserJavaTest {

  private GenericContext parse(ContentParser parser, String basePath, Class clazz) throws Exception {

    String classPath = clazz.getName().replace('.', '/');
    File sourceFile = new File(basePath + "/" + classPath + ".java");
    InputStream inputStream = new FileInputStream(sourceFile);
    try {
      GenericContext metadata = parser.parse(inputStream, sourceFile.length());
      assertEquals(clazz.getName(), metadata.getVariable(ContentParser.VARIABLE_NAME_TITLE));
      return metadata;
    } finally {
      inputStream.close();
    }
  }

  @Test
  public void testJavaParser() throws Exception {

    ContentParserJava parser = new ContentParserJava();
    parser.initialize();
    GenericContext metadata = parse(parser, "src/test/java/", ContentParserJavaTest.class);
    assertEquals("Jörg Hohwiller, Nobody Else",
        metadata.getVariable(ContentParser.VARIABLE_NAME_CREATOR));
    metadata = parse(parser, "src/main/java/", ContentParserJava.class);
    assertEquals("Joerg Hohwiller", metadata.getVariable(ContentParser.VARIABLE_NAME_CREATOR));
  }

}
