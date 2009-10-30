/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.SpringConfigsUtilCore;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.StringValueConverter;

/**
 * This is the test-case for {@link StringValueConverter} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComposedValueConverterSpringTest extends ComposedValueConverterTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ComposedValueConverter getComposedValueConverter() {

    return SpringContainerPool.getContainer(SpringConfigsUtilCore.UTIL_VALUE)
        .getComponent(ComposedValueConverter.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SpringConfigsUtilCore.UTIL_VALUE);
  }

}
