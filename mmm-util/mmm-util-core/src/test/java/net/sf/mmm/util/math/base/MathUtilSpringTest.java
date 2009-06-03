/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.math.api.MathUtil;

/**
 * This is the test-case for {@link MathUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class MathUtilSpringTest extends MathUtilTest {

  /** @see SpringContainerPool */
  private static final String SPRING_CONFIG = "net/sf/mmm/util/math/beans-util-math.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  public MathUtil getMathUtil() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(MathUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
