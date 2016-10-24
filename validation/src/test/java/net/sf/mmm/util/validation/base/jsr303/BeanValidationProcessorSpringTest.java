/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.jsr303;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.validation.impl.spring.UtilValidationSpringConfig;

/**
 * Test-case for {@link BeanValidationProcessor} using spring config.
 *
 * @author hohwille
 * @since 7.4.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilValidationSpringConfig.class })
public class BeanValidationProcessorSpringTest extends BeanValidationProcessorTest {

  @Inject
  private BeanValidationProcessor beanValidationProcessor;

  @Override
  protected BeanValidationProcessor getBeanValidationProcessor() {

    return this.beanValidationProcessor;
  }

}
