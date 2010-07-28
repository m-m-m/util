/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

import org.junit.AfterClass;

/**
 * This is the test-case for {@link ReflectionUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ReflectionUtilSpringTest extends ReflectionUtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public ReflectionUtil getReflectionUtil() {

    return SpringContainerPool.getInstance().getComponent(ReflectionUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.dispose();
  }

}
