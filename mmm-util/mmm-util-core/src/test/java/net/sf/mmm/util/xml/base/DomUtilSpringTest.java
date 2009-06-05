/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.xml.api.DomUtil;

/**
 * This is the test-case for {@link DomUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class DomUtilSpringTest extends DomUtilTest {

  /** @see SpringContainerPool */
  private static final String SPRING_CONFIG = "net/sf/mmm/util/xml/beans-util-xml.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  public DomUtil getDomUtil() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(DomUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
