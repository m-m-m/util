/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.dummy.AbstractPojo;
import net.sf.mmm.util.pojo.descriptor.impl.dummy.GenericPojo;
import net.sf.mmm.util.pojo.descriptor.impl.dummy.MyPojo;
import net.sf.mmm.util.reflect.api.GenericType;

import org.junit.Test;

/**
 * This is the abstract test-case for {@link net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder}
 * implementations using {@link MyPojo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class AbstractMyPojoDescriptorBuilderTest extends AbstractPojoDescriptorBuilderTest {

  /**
   * This method creates the {@link PojoDescriptorBuilder} to test.
   * 
   * @return the {@link PojoDescriptorBuilder}.
   */
  protected abstract PojoDescriptorBuilder getPojoDescriptorBuilder();

  /**
   * @return <code>true</code> if methods are introspected, <code>false</code> otherwise.
   */
  protected abstract boolean isMethodIntrostection();

  /**
   * @return <code>true</code> if fields are introspected, <code>false</code> otherwise.
   */
  protected boolean isFieldIntrostection() {

    return !isMethodIntrostection();
  }

  /**
   * Test of {@link PojoDescriptorBuilder} using external public class {@link MyPojo}.
   */
  @Test
  public void testPojoDescriptor() {

    checkPojoDescriptor(new MyPojo(), MyPojo.class);
  }

  protected <POJO extends MyPojo> void checkPojo(PojoDescriptor<POJO> pojoDescriptor, POJO pojoInstance,
      PojoDescriptorBuilder builder) {

    // test property "name"
    checkProperty(pojoDescriptor, "name", String.class, String.class);
    String name = "Emma";
    pojoDescriptor.setProperty(pojoInstance, "name", name);
    String retrievedName = (String) pojoDescriptor.getProperty(pojoInstance, "name");
    assertEquals(name, retrievedName);

    // test property "port"
    Integer port = Integer.valueOf(4242);
    assertNull(pojoDescriptor.getProperty(pojoInstance, "port"));
    pojoDescriptor.setProperty(pojoInstance, "port", port);
    Integer retrievedPort = (Integer) pojoDescriptor.getProperty(pojoInstance, "port");
    assertEquals(port, retrievedPort);
    // test using TypedProperty
    port = Integer.valueOf(42);
    pojoDescriptor.setProperty(pojoInstance, MyPojo.PROPERTY_PORT, port);
    retrievedPort = pojoDescriptor.getProperty(pojoInstance, MyPojo.PROPERTY_PORT);
    assertEquals(port, retrievedPort);

    // test property "flag"
    boolean flag = true;
    assertNull(pojoDescriptor.getProperty(pojoInstance, "flag"));
    pojoDescriptor.setProperty(pojoInstance, "flag", Boolean.valueOf(flag));
    assertEquals(Boolean.valueOf(flag), pojoDescriptor.getProperty(pojoInstance, "flag"));
    // test property "items"/"item"
    checkProperty(pojoDescriptor, "items", List.class, List.class);

    // generic access
    String indexedPropertyName = "items[0]";
    PojoPropertyAccessorNonArg getter = pojoDescriptor.getAccessor(indexedPropertyName,
        PojoPropertyAccessorNonArgMode.GET, true);
    assertEquals(String.class, getter.getPropertyClass());
    PojoPropertyAccessorOneArg setter = pojoDescriptor.getAccessor(indexedPropertyName,
        PojoPropertyAccessorOneArgMode.SET, true);
    assertEquals(String.class, setter.getPropertyClass());

    getter = pojoDescriptor.getAccessor("genericPojo", PojoPropertyAccessorNonArgMode.GET, true);
    GenericType genericPojoType = getter.getPropertyType();
    PojoDescriptor<?> genericPojoDescriptor = builder.getDescriptor(genericPojoType);
    getter = genericPojoDescriptor.getAccessor("element", PojoPropertyAccessorNonArgMode.GET, true);
    assertEquals(Long.class, getter.getPropertyClass());

    // test property "privateString"
    String secret = "secret";
    if (isFieldIntrostection()) {
      checkProperty(pojoDescriptor, "privateString", String.class, String.class);
      pojoDescriptor.setProperty(pojoInstance, "privateString", secret);
    } else {
      checkProperty(pojoDescriptor, "privateString", String.class, null);
      secret = "privateString";
    }
    String result = (String) pojoDescriptor.getProperty(pojoInstance, "privateString");
    assertEquals(secret, result);
    if (isFieldIntrostection() && !isMethodIntrostection()) {
      assertSame(secret, result);
    } else {
      // property is retrieved via getter...
      assertNotSame(secret, result);
    }
    // test property "renamedProperty"
    if (isMethodIntrostection()) {
      checkProperty(pojoDescriptor, "renamedProperty", String.class, String.class);
    }
    // test property "string"
    if (isFieldIntrostection()) {
      checkProperty(pojoDescriptor, "string", String.class, String.class);
      secret = "h5g/{h%k$z";
      pojoDescriptor.setProperty(pojoInstance, "string", secret);
      if (isMethodIntrostection()) {
        assertSame(secret, pojoDescriptor.getProperty(pojoInstance, "renamedProperty"));
      }
    }

    // test indirect property access
    Long genericPojoValue = Long.valueOf(1234);
    Long genericPojoResult = pojoDescriptor.getProperty(pojoInstance, MyPojo.PROPERTY_GENERICPOJO_ELEMENT);
    assertNull(genericPojoResult);
    pojoInstance.setGenericPojo(new GenericPojo<Long>());
    pojoDescriptor.setProperty(pojoInstance, MyPojo.PROPERTY_GENERICPOJO_ELEMENT, genericPojoValue);
    genericPojoResult = pojoDescriptor.getProperty(pojoInstance, MyPojo.PROPERTY_GENERICPOJO_ELEMENT);
    assertEquals(genericPojoValue, genericPojoResult);

    // check non-existent property
    try {
      pojoDescriptor.getAccessor("not-existent", PojoPropertyAccessorNonArgMode.GET, true);
      fail("Exception expected");
    } catch (PojoPropertyNotFoundException e) {
    }
    // check non-existent accessor
    try {
      pojoDescriptor.getAccessor("name", PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED, true);
      fail("Exception expected");
    } catch (PojoPropertyNotFoundException e) {
    }
  }

  protected <POJO extends MyPojo> void checkItems(PojoDescriptor<POJO> pojoDescriptor, POJO pojoInstance,
      boolean viaMethods) {

    // test property "items"
    List<String> myList = new ArrayList<String>();
    pojoDescriptor.setProperty(pojoInstance, "items", myList);
    assertSame(myList, pojoDescriptor.getProperty(pojoInstance, "items"));
    String item1 = "item1";
    if (viaMethods) {
      pojoDescriptor.addPropertyItem(pojoInstance, "item", item1);
    } else {
      pojoDescriptor.addPropertyItem(pojoInstance, "items", item1);
    }
    assertEquals(1, myList.size());
    assertEquals(item1, myList.get(0));
    if (viaMethods) {
      // ensure add was invoked via method instead of directly on list
      assertNotSame(item1, myList.get(0));
    } else {
      assertSame(item1, myList.get(0));
    }
    Object result = pojoDescriptor.getProperty(pojoInstance, "items[0]");
    assertEquals(item1, result);
    if (viaMethods) {
      // ensure get was invoked via indexed-getter instead of directly on list
      assertNotSame(item1, result);
    } else {
      assertSame(item1, result);
    }
    if (viaMethods) {
      pojoDescriptor.addPropertyItem(pojoInstance, "item", item1);
    } else {
      pojoDescriptor.addPropertyItem(pojoInstance, "items", item1);
    }
    String item2 = "item2";
    pojoDescriptor.setProperty(pojoInstance, "items[1]", item2);
    assertEquals(2, myList.size());
    assertEquals(2, pojoDescriptor.getPropertySize(pojoInstance, "items"));
    assertEquals(item2, myList.get(1));
    if (viaMethods) {
      // ensure set was invoked via indexed-setter instead of directly on list
      assertNotSame(item2, myList.get(1));
    } else {
      assertSame(item2, myList.get(1));
    }
    assertEquals(item2, pojoDescriptor.getProperty(pojoInstance, "items[1]"));
  }

  protected <POJO extends MyPojo> void checkValues(PojoDescriptor<POJO> pojoDescriptor, POJO pojoInstance,
      boolean viaMethods) {

    // test property "values"
    Map<String, String> values = new HashMap<String, String>();
    pojoDescriptor.setProperty(pojoInstance, "values", values);
    assertSame(values, pojoDescriptor.getProperty(pojoInstance, "values"));
    String key1 = "key1";
    String value1 = "value1";
    String property = "values['" + key1 + "']";
    pojoDescriptor.setProperty(pojoInstance, property, value1);
    assertEquals(value1, values.get(key1));
    if (viaMethods) {
      // ensure put was invoked via mapped setter instead of directly on map
      assertNotSame(value1, values.get(key1));
    } else {
      assertSame(value1, values.get(key1));
    }
    assertEquals(value1, pojoDescriptor.getProperty(pojoInstance, property));
    if (viaMethods) {
      // ensure get was invoked via mapped getter instead of directly on map
      assertNotSame(value1, pojoDescriptor.getProperty(pojoInstance, property));
    } else {
      assertSame(value1, pojoDescriptor.getProperty(pojoInstance, property));
    }

  }

  protected <POJO extends MyPojo> PojoDescriptor<POJO> checkPojoDescriptor(POJO pojoInstance, Class<POJO> pojoClass) {

    PojoDescriptorBuilder builder = getPojoDescriptorBuilder();
    PojoDescriptor<POJO> pojoDescriptor = builder.getDescriptor(pojoClass);
    assertEquals(pojoClass, pojoDescriptor.getPojoClass());
    // test property "class"
    if (isMethodIntrostection()) {
      assertEquals(pojoClass, pojoDescriptor.getProperty(pojoInstance, "class"));
    } else {
      assertNull(pojoDescriptor.getPropertyDescriptor("class"));
    }
    checkPojo(pojoDescriptor, pojoInstance, builder);
    // test property "port"
    checkPropertyPort(pojoDescriptor);
    // test property "flag"
    if (isFieldIntrostection() && !isMethodIntrostection()) {
      checkProperty(pojoDescriptor, "flag", Boolean.class, Boolean.class);
    } else {
      checkProperty(pojoDescriptor, "flag", Boolean.class, boolean.class);
    }
    // test property "items"/"item"
    checkProperty(pojoDescriptor, "items", List.class, List.class);

    checkItems(pojoDescriptor, pojoInstance, isMethodIntrostection());

    checkValues(pojoDescriptor, pojoInstance, isMethodIntrostection());

    // test property "genericPojo"
    checkProperty(pojoDescriptor, "genericPojo", GenericPojo.class, GenericPojo.class);

    // test polymorphism
    // TODO hohwille fix implementation and remove false...
    if (isMethodIntrostection() && false) {
      PojoDescriptor<AbstractPojo> abstractPojoDescriptor = builder.getDescriptor(AbstractPojo.class);
      checkPropertyPort(abstractPojoDescriptor);

      Integer port = Integer.valueOf(4242);
      assertNull(pojoDescriptor.getProperty(pojoInstance, "port"));
      pojoDescriptor.setProperty(pojoInstance, "port", port);
      Integer retrievedPort = (Integer) pojoDescriptor.getProperty(pojoInstance, "port");
      assertEquals(port, retrievedPort);
    }

    return pojoDescriptor;
  }

  private void checkPropertyPort(PojoDescriptor<?> pojoDescriptor) {

    String property = MyPojo.PROPERTY_PORT.getSegment();
    if (isFieldIntrostection() && !isMethodIntrostection()) {
      checkProperty(pojoDescriptor, property, Integer.class, Integer.class);
    } else {
      checkProperty(pojoDescriptor, property, Integer.class, int.class);
    }
  }

  static class TestPojo extends MyPojo {

    public String bar = "Bar";

    public String getFoo() {

      return "Foo";
    }
  }

}
