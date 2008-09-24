/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.impl.dummy.MyPojo;
import net.sf.mmm.util.reflect.api.VisibilityModifier;

/**
 * This is the test-case for {@link PublicMethodPojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PrivateMethodPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected PojoDescriptorBuilder getPojoDescriptorBuilder() {

    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder
        .setMethodIntrospector(new PojoMethodIntrospectorImpl(VisibilityModifier.PRIVATE, false));
    builder.initialize();
    return builder;
  }

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilder builder = getPojoDescriptorBuilder();
    PojoDescriptor<MyPojo> pojoDescriptor = builder.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoClass());
    MyPojo pojoInstance = new MyPojo();
    // test property "class"
    assertEquals(MyPojo.class, pojoDescriptor.getProperty(pojoInstance, "class"));
    checkPojo(pojoDescriptor, pojoInstance, builder);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, int.class);
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, boolean.class);

    checkItems(pojoDescriptor, pojoInstance, true);

    checkProperty(pojoDescriptor, "privateProperty", String.class, String.class);

    checkValues(pojoDescriptor, pojoInstance, true);
  }

}
