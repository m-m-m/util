/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import org.junit.Test;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.resource.base.ClasspathResourceTest;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class DataResourceFactoryTest {

  public DataResourceFactory getDataResourceFactory() {

    return new DataResourceFactoryImpl();
  }

  @Test
  public void testClasspathResource() throws Exception {

    String resourceUri = ClasspathResource.SCHEME_PREFIX
        + ClasspathResource.class.getName().replace('.', '/') + ".txt";
    DataResource resource = getDataResourceFactory().createDataResource(resourceUri);
    ClasspathResourceTest.verifyResource(resource);
  }
}
