/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.context.api.GenericContextFactory;

/**
 * This is the test-case for {@link GenericContextFactory} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class GenericContextFactorySpringTest extends GenericContextFactoryTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericContextFactory getFactory() {

    return SpringContainerPool.getInstance().get(GenericContextFactory.class);
  }

}
