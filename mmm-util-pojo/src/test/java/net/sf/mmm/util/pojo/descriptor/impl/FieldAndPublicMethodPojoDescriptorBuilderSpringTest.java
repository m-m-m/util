/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.util.component.impl.SpringContainerPool;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the test-case for {@link PojoDescriptorBuilder} using combined introspection of private fields and
 * public methods via spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldAndPublicMethodPojoDescriptorBuilderSpringTest extends FieldAndPublicMethodPojoDescriptorBuilderTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderFactory factory = SpringContainerPool.getInstance().get(
        PojoDescriptorBuilderFactory.class);
    return factory.createDescriptorBuilder(VisibilityModifier.PUBLIC, VisibilityModifier.PRIVATE);
  }
}
