/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.lang.api.BasicUtil;

import org.junit.AfterClass;

/**
 * This is the test-case for {@link BasicUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtilSpringTest extends BasicUtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public BasicUtil getBasicUtil() {

    return SpringContainerPool.getInstance().getComponent(BasicUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.dispose();
  }

}
