/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * This is the {@link TestCase} for the class {@link ClasspathUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ClasspathResourceTest extends TestCase {

  public void verifyResource(DataResource resource) throws Exception {

    assertTrue(resource.isAvailable());
    assertEquals(41, resource.getSize());
    InputStream in = in = resource.openStream();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line = reader.readLine();
      assertEquals("This is a resource loaded from classpath.", line);
      line = reader.readLine();
      assertNull(line);
    } finally {
      in.close();
    }
  }

  @Test
  public void testClasspathResource() throws Exception {

    verifyResource(new ClasspathResource(ClasspathResource.class, ".txt", true));
    verifyResource(new ClasspathResource(ClasspathResource.class, "ClasspathResource.txt", false));
    verifyResource(new ClasspathResource(ClasspathResource.class.getPackage(),
        "ClasspathResource.txt"));
    verifyResource(new ClasspathResource(ClasspathResource.class.getName().replace('.', '/')
        + ".txt"));
  }
}
