/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import org.junit.Test;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.base.NoPojoMethodIntrospector;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the test-case for {@link FieldPojoDescriptorBuilder}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FieldPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder.setMethodIntrospector(new NoPojoMethodIntrospector());
    builder.setFieldIntrospector(new PojoFieldIntrospectorImpl(VisibilityModifier.PRIVATE, false));
    builder.initialize();
    return builder;
  }

  @Override
  protected boolean isMethodIntrostection() {

    return false;
  }

  /**
   * Test of {@link PojoDescriptorBuilder} using external public class {@link TestPojo}.
   */
  @Test
  public void testPojoDescriptorInnerClass() {

    TestPojo pojoInstance = new TestPojo();
    PojoDescriptor<TestPojo> pojoDescriptor = checkPojoDescriptor(pojoInstance, TestPojo.class);
    Object bar = pojoDescriptor.getProperty(pojoInstance, "bar");
    assertEquals("Bar", bar);
  }

}
