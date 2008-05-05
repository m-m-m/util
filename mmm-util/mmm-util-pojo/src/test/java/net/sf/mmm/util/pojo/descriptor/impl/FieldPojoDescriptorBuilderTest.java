/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.pojo.descriptor.impl.PojoFieldIntrospectorImpl;
import net.sf.mmm.util.pojo.descriptor.impl.dummy.MyPojo;
import net.sf.mmm.util.reflect.VisibilityModifier;

/**
 * This is the test-case for {@link FieldPojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FieldPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder.setFieldIntrospector(new PojoFieldIntrospectorImpl(VisibilityModifier.PRIVATE, false));
    builder.initialize();
    PojoDescriptor<MyPojo> pojoDescriptor = builder.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoClass());
    MyPojo pojoInstance = new MyPojo();
    checkPojo(pojoDescriptor, pojoInstance, builder);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, Integer.class);
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, Boolean.class);
    // test property "items"/"item"
    checkProperty(pojoDescriptor, "items", List.class, List.class);

    checkItems(pojoDescriptor, pojoInstance, false);

    checkValues(pojoDescriptor, pojoInstance, false);
  }

}
