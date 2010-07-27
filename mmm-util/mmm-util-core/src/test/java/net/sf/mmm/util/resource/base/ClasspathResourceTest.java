/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;
import net.sf.mmm.util.resource.api.DataResource;

import org.junit.Test;

/**
 * This is the test-case for the class {@link ClasspathUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ClasspathResourceTest {

  public static void verifyResource(DataResource resource) throws Exception {

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

    ClasspathResource resource = new ClasspathResource(ClasspathResource.class, ".txt", true);
    verifyResource(resource);
    verifyResource(new ClasspathResource(ClasspathResource.class, //
        ClasspathResource.class.getSimpleName() + ".txt", false));
    verifyResource(new ClasspathResource(ClasspathResource.class.getPackage(),
        ClasspathResource.class.getSimpleName() + ".txt"));
    String name = ClasspathResource.class.getSimpleName() + ".txt";
    String absoluteClasspath = ClasspathResource.class.getPackage().getName().replace('.', '/')
        + "/" + name;
    verifyResource(new ClasspathResource(absoluteClasspath));
    Assert.assertEquals(name, resource.getName());
    Assert.assertEquals(absoluteClasspath, resource.getPath());
    Assert.assertEquals(ClasspathResource.SCHEME_PREFIX + absoluteClasspath, resource.getUri());
  }
}
