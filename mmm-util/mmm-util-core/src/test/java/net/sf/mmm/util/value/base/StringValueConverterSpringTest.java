/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import org.junit.AfterClass;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.value.api.StringValueConverter;

/**
 * This is the test-case for {@link StringValueConverter} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringValueConverterSpringTest extends StringValueConverterTest {

  /** The spring config for util.value */
  public static final String SPRING_CONFIG = "net/sf/mmm/util/value/beans-util-value.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  public StringValueConverter getStringValueConverter() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(StringValueConverter.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Iso8601Util getIso8601Util() {

    return SpringContainerPool.getContainer(SPRING_CONFIG).getComponent(Iso8601Util.class);

  }

  /**
   * This method is invoked after all tests of this class have completed.
   */
  @AfterClass
  public static void tearDown() {

    SpringContainerPool.disposeContainer(SPRING_CONFIG);
  }

}
