/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;

/**
 * This is the test-case for {@link PojoDescriptorBuilderFactory#createPrivateFieldDescriptorBuilder()} using
 * spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldPojoDescriptorBuilderSpringTest extends FieldPojoDescriptorBuilderTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderFactory factory = SpringContainerPool.getInstance().getComponent(
        PojoDescriptorBuilderFactory.class);
    return factory.createPrivateFieldDescriptorBuilder();
  }
}
