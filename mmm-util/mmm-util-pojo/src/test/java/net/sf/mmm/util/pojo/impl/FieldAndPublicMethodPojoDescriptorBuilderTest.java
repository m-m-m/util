/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.impl.dummy.MyPojo;

import junit.framework.TestCase;

/**
 * This is the test-case for {@link PublicMethodPojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FieldAndPublicMethodPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilder factory = new FieldAndPublicMethodPojoDescriptorBuilder();
    PojoDescriptor<MyPojo> pojoDescriptor = factory.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoType());
    MyPojo pojoInstance = new MyPojo();
    // test property "class"
    assertEquals(MyPojo.class, pojoDescriptor.getProperty(pojoInstance, "class"));
    checkPojo(pojoDescriptor, pojoInstance);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, int.class);
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, boolean.class);
    // test property "items"/"item"
    
    // TODO: create add helper and move to abstract parent
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
    
    // test property "privateString"
    String secret = "secret";
    checkProperty(pojoDescriptor, "privateString", String.class, String.class);
    pojoDescriptor.setProperty(pojoInstance, "privateString", secret);
    String result = (String) pojoDescriptor.getProperty(pojoInstance, "privateString");
    assertEquals(secret, result);
    // property is retrieved via getter...
    assertNotSame(secret, result);
    // test property "renamedProperty"
    checkProperty(pojoDescriptor, "renamedProperty", String.class, String.class);
    // test property "string"
    checkProperty(pojoDescriptor, "string", String.class, String.class);
    secret = "h5g/{h%k$z";
    pojoDescriptor.setProperty(pojoInstance, "string", secret);
    assertSame(secret, pojoDescriptor.getProperty(pojoInstance, "renamedProperty"));
    
  }

}
