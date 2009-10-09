/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.context.api.GenericContextFactory;

/**
 * This is the test-case for {@link GenericContextFactory} configured using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class GenericContextFactorySpringTest extends GenericContextFactoryTest {

  /** */
  private static final String SPRING_CONFIG = "net/sf/mmm/util/context/beans-util-context.xml";

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericContextFactory getFactory() {

    return SpringContainerPool.getContainer(SPRING_CONFIG)
        .getComponent(GenericContextFactory.class);
  }

}
