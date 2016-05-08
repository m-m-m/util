/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.impl.spring.UtilContextSpringConfig;

/**
 * This is the test-case for {@link GenericContextFactory} configured using spring.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UtilContextSpringConfig.class })
public class GenericContextFactorySpringTest extends GenericContextFactoryTest {

  @Inject
  private GenericContextFactory genericContextFactory;

  @Override
  protected GenericContextFactory getFactory() {

    return this.genericContextFactory;
  }

}
