/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;

/**
 * This is the test-case for {@link StringValueConverter} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilValueSpringConfig.class })
public class StringValueConverterSpringTest extends StringValueConverterTest {

  @Inject
  private StringValueConverter stringValueConverter;

  @Inject
  private Iso8601Util iso8601Util;

  @Override
  public StringValueConverter getStringValueConverter() {

    return this.stringValueConverter;
  }

  @Override
  protected Iso8601Util getIso8601Util() {

    return this.iso8601Util;

  }

}
