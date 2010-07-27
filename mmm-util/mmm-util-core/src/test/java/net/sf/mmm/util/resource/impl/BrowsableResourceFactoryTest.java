/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.ClasspathResourceTest;

import org.junit.Test;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class BrowsableResourceFactoryTest {

  public BrowsableResourceFactory getBrowsableResourceFactory() {

    BrowsableResourceFactoryImpl impl = new BrowsableResourceFactoryImpl();
    impl.initialize();
    return impl;
  }

  @Test
  public void testClasspathResource() throws Exception {

    String resourceUri = ClasspathResource.SCHEME_PREFIX
        + ClasspathResource.class.getName().replace('.', '/') + ".txt";
    DataResource resource = getBrowsableResourceFactory().createDataResource(resourceUri);
    ClasspathResourceTest.verifyResource(resource);

    resource = getBrowsableResourceFactory().createDataResource("file:///test");
    resource = resource.navigate("foo/bar.txt").navigate(resourceUri);
    ClasspathResourceTest.verifyResource(resource);
  }
}
