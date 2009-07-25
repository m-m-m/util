/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.lang.api.BasicUtil;

/**
 * This is the test-case for {@link BasicUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtilSpringTest extends BasicUtilTest {

  /** @see #getBasicUtil() */
  public static final String SPRING_CONFIG = "net/sf/mmm/util/lang/beans-util-lang.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  public BasicUtil getBasicUtil() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(BasicUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
