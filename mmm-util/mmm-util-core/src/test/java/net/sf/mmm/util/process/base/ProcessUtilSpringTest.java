/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.process.base;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.process.api.ProcessUtil;

/**
 * This is the test-case for {@link ProcessUtilImpl} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ProcessUtilSpringTest extends ProcessUtilTest {

  /** @see SpringContainerPool */
  private static final String SPRING_CONFIG = "net/sf/mmm/util/process/beans-util-process.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  public ProcessUtil getProcessUtil() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(ProcessUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
