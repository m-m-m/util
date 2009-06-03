/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;

/**
 * This is the test-case for {@link CollectionReflectionUtil} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CollectionReflectionUtilSpringTest extends CollectionReflectionUtilTest {

  private static final String SPRING_CONFIG = AnnotationUtilSpringTest.SPRING_CONFIG;

  /**
   * {@inheritDoc}
   */
  @Override
  public CollectionReflectionUtil getCollectionReflectionUtil() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(
        CollectionReflectionUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
