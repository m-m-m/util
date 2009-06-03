/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.resource.api.DataResourceFactory;

/**
 * This is the test-case for the class {@link DataResourceFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DataResourceFactorySpringTest extends DataResourceFactoryTest {

  /** @see SpringContainerPool */
  private static final String SPRING_CONFIG = "net/sf/mmm/util/resource/beans-util-resource.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  public DataResourceFactory getDataResourceFactory() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(DataResourceFactory.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
