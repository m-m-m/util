/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.mmm.util.reflect.VisibilityModifier;
import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.PojoMethodIntrospectorImpl;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.dummy.MyPojo;

/**
 * This is the test-case for {@link PublicMethodPojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PrivateMethodPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder
        .setMethodIntrospector(new PojoMethodIntrospectorImpl(VisibilityModifier.PRIVATE, false));
    builder.initialize();
    PojoDescriptor<MyPojo> pojoDescriptor = builder.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoType());
    MyPojo pojoInstance = new MyPojo();
    // test property "class"
    assertEquals(MyPojo.class, pojoDescriptor.getProperty(pojoInstance, "class"));
    checkPojo(pojoDescriptor, pojoInstance);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, int.class);
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, boolean.class);

    List<String> myList = new ArrayList<String>();
    pojoDescriptor.setProperty(pojoInstance, "items", myList);
    assertSame(myList, pojoDescriptor.getProperty(pojoInstance, "items"));
    String item1 = "item1";
    pojoDescriptor.addPropertyItem(pojoInstance, "item", item1);
    assertEquals(1, myList.size());
    assertEquals(item1, myList.get(0));
    String item2 = "item2";
    pojoDescriptor.addPropertyItem(pojoInstance, "item", item2);
    assertEquals(2, myList.size());
    assertEquals(item2, myList.get(1));

    checkProperty(pojoDescriptor, "privateProperty", String.class, String.class);
  }

}
