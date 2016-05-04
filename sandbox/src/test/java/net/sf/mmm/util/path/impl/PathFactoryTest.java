/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import net.sf.mmm.util.path.api.PathFactory;
import net.sf.mmm.util.path.api.PathUri;
import net.sf.mmm.util.resource.base.ClasspathResource;

/**
 * This is the test-case for {@link PathFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PathFactoryTest extends Assert {

  /**
   * This method gets the {@link PathFactory} instance to test.
   *
   * @return the {@link PathFactory}.
   */
  public PathFactory getPathFactory() {

    PathFactoryImpl impl = new PathFactoryImpl();
    impl.initialize();
    return impl;
  }

  @Test
  public void testFileResource() throws Exception {

    String filename = "non-existent-file";
    String path = "/" + filename;
    String resourceUri = "file://" + path;
    Path resource = getPathFactory().createPath(resourceUri);
    assertEquals(filename, resource.getFileName().toString());
    // will not work on windows by intention - see javadoc...
    // assertEquals(path, resource.getPath());
    // assertEquals(resourceUri, resource.getUri());
    assertFalse(Files.exists(resource));
    assertFalse(Files.isDirectory(resource));
    // assertNull(Files.getLastModifiedTime(resource));
    try {
      Files.size(resource);
      fail("Exception expected");
    } catch (IOException e) {
      // OK
      assertTrue(e.getMessage().contains(filename));
    }
  }

  @Test
  @Ignore
  public void testUrlResource() throws Exception {

    String filename = "page.html";
    String path = "/folder/subfolder/" + filename;
    String host = "foo.bar.xyz";
    String resourceUri = "http://" + host + path;
    Path resource = getPathFactory().createPath(resourceUri);
    assertEquals(filename, resource.getFileName());
    assertEquals(path, resource.getParent().toString());
    assertEquals(resourceUri, resource.toString());
    assertFalse(Files.exists(resource));
    // try {
    // resource.getSize();
    // Assert.fail("Exception expected");
    // } catch (ResourceNotAvailableException e) {
    // // OK
    // assertTrue(e.getMessage().contains(filename));
    // }
  }

  @Test
  @Ignore
  public void testClasspathResource() throws Exception {

    PathFactory pathFactory = getPathFactory();
    String resourceUri = PathUri.SCHEME_PREFIX_CLASSPATH + ClasspathResource.class.getName().replace('.', '/')
        + ".txt";
    Path resource = pathFactory.createPath(resourceUri);
    verifyResource(resource);

    resource = pathFactory.createPath("file:///test");
    resource = pathFactory.createPath(resource, "foo/bar.txt");
    resource = pathFactory.createPath(resource, resourceUri);
    verifyResource(resource);

    resource = pathFactory.createPath("classpath:META-INF/maven/org.slf4j/slf4j-api/pom.properties");
    assertTrue(Files.exists(resource));
    assertEquals(104, Files.size(resource));
  }

  public static void verifyResource(Path resource) throws Exception {

    assertTrue(Files.exists(resource));
    assertEquals(41, Files.size(resource));

    try (InputStream in = Files.newInputStream(resource)) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String line = reader.readLine();
      assertEquals("This is a resource loaded from classpath.", line);
      line = reader.readLine();
      assertNull(line);
    }
  }

}
