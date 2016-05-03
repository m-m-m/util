/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;

import org.junit.Test;

/**
 * This is the test-case for {@link PojoDescriptorBuilder} using public method introspection.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PublicMethodPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder.initialize();
    return builder;
  }

  @Override
  protected boolean isMethodIntrostection() {

    return true;
  }

  /**
   * Test of {@link PojoDescriptorBuilder} using external public class {@link TestPojo}.
   */
  @Test
  public void testPojoDescriptorInnerClass() {

    TestPojo pojoInstance = new TestPojo();
    PojoDescriptor<TestPojo> pojoDescriptor = checkPojoDescriptor(pojoInstance, TestPojo.class);
    Object foo = pojoDescriptor.getProperty(pojoInstance, "foo");
    assertEquals("Foo", foo);
  }

}
