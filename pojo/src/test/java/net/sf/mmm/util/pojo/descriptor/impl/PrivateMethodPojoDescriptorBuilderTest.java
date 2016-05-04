/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the test-case for {@link PojoDescriptorBuilder} using private method introspection.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PrivateMethodPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder.setMethodIntrospector(new PojoMethodIntrospectorImpl(VisibilityModifier.PRIVATE, false));
    builder.initialize();
    return builder;
  }

  @Override
  protected boolean isMethodIntrostection() {

    return true;
  }

}
