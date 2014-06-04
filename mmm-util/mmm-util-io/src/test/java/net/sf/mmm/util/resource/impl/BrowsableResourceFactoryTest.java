/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.Date;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class BrowsableResourceFactoryTest extends Assert {

  /**
   * This method gets the {@link BrowsableResourceFactory} instance to test.
   *
   * @return the {@link BrowsableResourceFactory}.
   */
  public BrowsableResourceFactory getBrowsableResourceFactory() {

    BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
    impl.initialize();
    return impl;
  }

  @Test
  public void testFileResource() throws Exception {

    String filename = "non-existent-file";
    String path = "/" + filename;
    String resourceUri = "file://" + path;
    BrowsableResource resource = getBrowsableResourceFactory().createBrowsableResource(resourceUri);
    assertEquals(filename, resource.getName());
    // will not work on windows by intention - see javadoc...
    // assertEquals(path, resource.getPath());
    // assertEquals(resourceUri, resource.getUri());
    assertFalse(resource.isAvailable());
    assertFalse(resource.isFolder());
    assertNull(resource.isModifiedSince(new Date()));
    try {
      resource.getSize();
      fail("Exception expected");
    } catch (ResourceNotAvailableException e) {
      // OK
      assertTrue(e.getMessage().contains(filename));
    }
  }

  @Test
  public void testUrlResource() throws Exception {

    String filename = "page.html";
    String path = "/folder/subfolder/" + filename;
    String host = "foo.bar.xyz";
    String resourceUri = "http://" + host + path;
    DataResource resource = getBrowsableResourceFactory().createDataResource(resourceUri);
    assertEquals(filename, resource.getName());
    assertEquals(path, resource.getPath());
    assertEquals(resourceUri, resource.getUri());
    assertFalse(resource.isAvailable());
    // try {
    // resource.getSize();
    // Assert.fail("Exception expected");
    // } catch (ResourceNotAvailableException e) {
    // // OK
    // assertTrue(e.getMessage().contains(filename));
    // }
  }

  @Test
  public void testClasspathResource() throws Exception {

    BrowsableResourceFactory factory = getBrowsableResourceFactory();
    DataResource resource = factory.createDataResource("file:///test");
    assertEquals("test", resource.getName());
    resource = resource.navigate("foo/bar.txt");
    assertEquals("bar.txt", resource.getName());
    resource = resource.navigate("classpath:META-INF/maven/org.slf4j/slf4j-api/pom.properties");
    assertTrue(resource.isAvailable());
    assertEquals(108, resource.getSize());
  }
}
