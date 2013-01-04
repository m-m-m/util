/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.StringValueConverter;

import org.junit.AfterClass;

/**
 * This is the test-case for {@link StringValueConverter} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComposedValueConverterSpringTest extends ComposedValueConverterTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ComposedValueConverter getComposedValueConverter() {

    return SpringContainerPool.getInstance().get(ComposedValueConverter.class);
  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.dispose();
  }

}
