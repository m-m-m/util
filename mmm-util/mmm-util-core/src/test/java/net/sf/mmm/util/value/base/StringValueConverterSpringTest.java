/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.value.api.StringValueConverter;

import org.junit.AfterClass;

/**
 * This is the test-case for {@link StringValueConverter} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringValueConverterSpringTest extends StringValueConverterTest {

  /**
   * {@inheritDoc}
   */
  @Override
  public StringValueConverter getStringValueConverter() {

    return SpringContainerPool.getInstance().getComponent(StringValueConverter.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Iso8601Util getIso8601Util() {

    return SpringContainerPool.getInstance().getComponent(Iso8601Util.class);

  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.dispose();
  }

}
