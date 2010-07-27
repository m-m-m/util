/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.SpringConfigsUtilCore;
import net.sf.mmm.util.resource.api.BrowsableResourceFactory;
import net.sf.mmm.util.resource.api.DataResourceFactory;

import org.junit.AfterClass;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BrowsableResourceFactorySpringTest extends BrowsableResourceFactoryTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public BrowsableResourceFactory getBrowsableResourceFactory() {

    return SpringContainerPool.getContainer(SpringConfigsUtilCore.UTIL_RESOURCE).getComponent(
        BrowsableResourceFactory.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SpringConfigsUtilCore.UTIL_RESOURCE);
  }

}
