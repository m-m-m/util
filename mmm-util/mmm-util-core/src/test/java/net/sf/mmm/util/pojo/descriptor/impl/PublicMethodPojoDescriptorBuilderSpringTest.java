/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.SpringConfigsUtilCore;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;

/**
 * This is the test-case for
 * {@link PojoDescriptorBuilderFactory#createPublicMethodDescriptorBuilder()}
 * using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PublicMethodPojoDescriptorBuilderSpringTest extends
    PublicMethodPojoDescriptorBuilderTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderFactory factory = SpringContainerPool.getContainer(
        SpringConfigsUtilCore.UTIL_POJO_DESCRIPTOR)
        .getComponent(PojoDescriptorBuilderFactory.class);
    return factory.createPublicMethodDescriptorBuilder();
  }

}
