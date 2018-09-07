/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.impl.spring.UtilValueSpringConfig;

/**
 * This is the test-case for {@link StringValueConverter} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilValueSpringConfig.class })
public class ComposedValueConverterSpringTest extends ComposedValueConverterTest {

  @Inject
  private ComposedValueConverter composedValueConverter;

  @Override
  protected ComposedValueConverter getComposedValueConverter() {

    return this.composedValueConverter;
  }

}
