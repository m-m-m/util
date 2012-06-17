/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.xml.api.DomUtil;

import org.junit.AfterClass;

/**
 * This is the test-case for {@link DomUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class DomUtilSpringTest extends DomUtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public DomUtil getDomUtil() {

    return SpringContainerPool.getInstance().getComponent(DomUtil.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.dispose();
  }

}
