/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.Date;

import net.sf.mmm.util.resource.api.BrowsableResource;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.ClasspathResourceTest;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class BrowsableResourceFactoryTest {

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
    Assert.assertEquals(filename, resource.getName());
    // will not work on windows by intention - see javadoc...
    // Assert.assertEquals(path, resource.getPath());
    // Assert.assertEquals(resourceUri, resource.getUri());
    Assert.assertFalse(resource.isAvailable());
    Assert.assertFalse(resource.isFolder());
    Assert.assertNull(resource.isModifiedSince(new Date()));
    try {
      resource.getSize();
      Assert.fail("Exception expected");
    } catch (ResourceNotAvailableException e) {
      // OK
      Assert.assertTrue(e.getMessage().contains(filename));
    }
  }

  @Test
  public void testUrlResource() throws Exception {

    String filename = "page.html";
    String path = "/folder/subfolder/" + filename;
    String host = "foo.bar.xyz";
    String resourceUri = "http://" + host + path;
    DataResource resource = getBrowsableResourceFactory().createDataResource(resourceUri);
    Assert.assertEquals(filename, resource.getName());
    Assert.assertEquals(path, resource.getPath());
    Assert.assertEquals(resourceUri, resource.getUri());
    Assert.assertFalse(resource.isAvailable());
    // try {
    // resource.getSize();
    // Assert.fail("Exception expected");
    // } catch (ResourceNotAvailableException e) {
    // // OK
    // Assert.assertTrue(e.getMessage().contains(filename));
    // }
  }

  @Test
  public void testClasspathResource() throws Exception {

    String resourceUri = ClasspathResource.SCHEME_PREFIX + ClasspathResource.class.getName().replace('.', '/') + ".txt";
    DataResource resource = getBrowsableResourceFactory().createDataResource(resourceUri);
    ClasspathResourceTest.verifyResource(resource);

    resource = getBrowsableResourceFactory().createDataResource("file:///test");
    resource = resource.navigate("foo/bar.txt").navigate(resourceUri);
    ClasspathResourceTest.verifyResource(resource);
  }

}
