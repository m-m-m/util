/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.pojo.api.PojoFactory;

/**
 * This is the test-case for {@link PojoFactory} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoFactorySpringTest extends PojoFactoryTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoFactory getPojoFactory() {

    return SpringContainerPool.getInstance().get(PojoFactory.class);
  }

}
