/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sf.mmm.util.reflect.pojo.path.base.AbstractPojoPathFunction;
import net.sf.mmm.util.reflect.pojo.path.base.DefaultPojoPathContext;
import net.sf.mmm.util.reflect.pojo.path.base.DefaultPojoPathFunctionManager;

/**
 * This is the abstract test-case for {@link PojoPathNavigator} implementations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public abstract class PojoPathNavigatorTest {

  protected abstract PojoPathNavigator createNavigator();

  @Test
  public void testGetFlat() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    // test using map
    Map<String, Integer> map = new HashMap<String, Integer>();
    String key = "key";
    // test get on empty map
    Object result = navigator.get(map, key, PojoPathMode.RETURN_IF_NULL, context);
    assertNull(result);
    // test get for value in map
    Integer value = Integer.valueOf(42);
    map.put(key, value);
    result = navigator.get(map, key, PojoPathMode.RETURN_IF_NULL, context);
    assertSame(value, result);

    String one = "first";
    String two = "second";
    // test using list
    List<String> list = new ArrayList<String>();
    // test get on empty list
    result = navigator.get(list, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertNull(result);
    // test get for value in list
    list.add(one);
    result = navigator.get(list, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(one, result);
    list.add(two);
    result = navigator.get(list, "1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(two, result);
    list.remove(0);
    // works with active cache cause hashCode of the initial pojo (list) changed
    result = navigator.get(list, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(two, result);

    // test using array
    one = "number one";
    two = "mr. two";
    String[] array = new String[] { one, two };
    result = navigator.get(array, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(one, result);
    result = navigator.get(array, "1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(two, result);

    // test using pojo
    MyPojo myPojo = new MyPojo();
    Integer foo = Integer.valueOf(42);
    myPojo.setFoo(foo.intValue());
    result = navigator.get(myPojo, "foo", PojoPathMode.RETURN_IF_NULL, context);
    assertEquals(foo, result);

    // test using function
    FooOrBarFunction function = new FooOrBarFunction();
    DefaultPojoPathFunctionManager functionManager = new DefaultPojoPathFunctionManager();
    String functionName = "fooOrBar";
    functionManager.registerFunction(function, functionName);
    // it is bad style to change the context on the run...
    DefaultPojoPathContext defaultContext = new DefaultPojoPathContext();
    defaultContext.setAdditionalFunctionManager(functionManager);
    context = defaultContext;
    result = navigator.get(myPojo, PojoPathFunction.FUNCTION_NAME_PREFIX + functionName,
        PojoPathMode.RETURN_IF_NULL, context);
    assertEquals(foo, result);
    Integer bar = Integer.valueOf(84);
    myPojo.setBar(bar.intValue());
    myPojo.setFlag();
    result = navigator.get(myPojo, PojoPathFunction.FUNCTION_NAME_PREFIX + functionName,
        PojoPathMode.RETURN_IF_NULL, context);
    assertEquals(bar, result);
  }

  @Test
  public void testGetTree() {

    PojoPathNavigator navigator = createNavigator();
    FooOrBarFunction function = new FooOrBarFunction();
    DefaultPojoPathFunctionManager functionManager = new DefaultPojoPathFunctionManager();
    String functionName = "fooOrBar";
    functionManager.registerFunction(function, functionName);
    DefaultPojoPathContext defaultContext = new DefaultPojoPathContext();
    defaultContext.setAdditionalFunctionManager(functionManager);
    PojoPathContext context = defaultContext;

    // create test objects
    Map<String, MyPojo[]> map = new HashMap<String, MyPojo[]>();
    String key = "foo";
    MyPojo[] array = new MyPojo[1];
    map.put(key, array);
    array[0] = new MyPojo();
    MyPojo parent = new MyPojo();
    array[0].setParent(parent);
    MyPojo root = new MyPojo();
    parent.setParent(root);
    Integer foo = Integer.valueOf(42);
    root.setFoo(foo.intValue());

    // do the test
    Object result;
    result = navigator.get(map, key + ".0.parent.parent.foo", PojoPathMode.RETURN_IF_NULL, context);
    assertEquals(foo, result);
    result = navigator.get(map, key + ".0.parent.parent.@" + functionName,
        PojoPathMode.RETURN_IF_NULL, context);
    assertEquals(foo, result);
  }

  @Test
  public void testCreate() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    CollectionPojo pojo = new CollectionPojo();
    String key = "key";
    Object result;

    // simple test: create in POJO
    result = navigator.get(pojo, "string", PojoPathMode.CREATE_IF_NULL, context);
    assertSame(pojo.getString(), result);

    // create through generic POJO-property including map, list and array...
    int listIndex = 1;
    int arrayIndex = 2;
    result = navigator.get(pojo, "map." + key + "." + listIndex + "." + arrayIndex,
        PojoPathMode.CREATE_IF_NULL, context);
    assertEquals("", result);
    String value = pojo.getMap().get(key).get(listIndex)[arrayIndex];
    assertSame(value, result);
    assertEquals(2, pojo.getMap().get(key).size());
    // rerun with different indices...
    listIndex = 2;
    arrayIndex = 1;
    result = navigator.get(pojo, "map." + key + "." + listIndex + "." + arrayIndex,
        PojoPathMode.CREATE_IF_NULL, context);
    assertEquals("", result);
    value = pojo.getMap().get(key).get(listIndex)[arrayIndex];
    assertSame(value, result);
    assertEquals(3, pojo.getMap().get(key).size());
  }

  @Test
  public void testSetArrayLengthOverflow() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    // prepare test-data
    Map<String, String[]> map = new HashMap<String, String[]>();
    String key = "foo";
    String one = "first";
    String two = "second";
    String three = "third";
    String[] array = new String[] { one, two };
    map.put(key, array);

    // do sanity check
    Object result;
    result = navigator.get(map, key, PojoPathMode.RETURN_IF_NULL, context);
    assertSame(array, result);
    result = navigator.get(map, key + ".0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(one, result);
    result = navigator.get(map, key + ".1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(two, result);
    result = navigator.get(map, key + ".2", PojoPathMode.RETURN_IF_NULL, context);
    assertNull(result);

    // test array overflow set
    result = navigator.set(map, key + ".2", PojoPathMode.FAIL_IF_NULL, context, three);
    assertNull(result);
    String[] newArray = map.get(key);
    assertNotNull(newArray);
    assertTrue(newArray.length == 3);
    assertEquals(one, newArray[0]);
    assertEquals(two, newArray[1]);
    assertEquals(three, newArray[2]);
    result = navigator.get(map, key + ".2", PojoPathMode.FAIL_IF_NULL, context);
  }

  @Test
  public void testCaching() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    // prepare test-data
    Map<String, String[]> map = new HashMap<String, String[]>();
    String key = "foo";
    String one = "first";
    String two = "second";
    String three = "third";
    String four = "fourth";
    String[] array = new String[] { one, two };
    map.put(key, array);

    // do sanity check
    Object result;
    result = navigator.get(map, key, PojoPathMode.RETURN_IF_NULL, context);
    assertSame(array, result);
    result = navigator.get(map, key + ".0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(one, result);
    result = navigator.get(map, key + ".1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(two, result);

    // change content via navigator...
    result = navigator.set(map, key + ".0", PojoPathMode.FAIL_IF_NULL, context, three);
    assertSame(one, result);
    result = navigator.get(map, key + ".0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(three, result);
    result = navigator.set(map, key + ".1", PojoPathMode.FAIL_IF_NULL, context, four);
    assertSame(two, result);
    result = navigator.get(map, key + ".1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(four, result);

    // change content outside navigator and get again...
    array[0] = one;
    array[1] = two;
    result = navigator.get(map, key + ".0", PojoPathMode.RETURN_IF_NULL, context);
    // just for documentation - we do NOT want to assert a bad behaviour...
    // assertSame(three, result);
    result = navigator.get(map, key + ".1", PojoPathMode.RETURN_IF_NULL, context);
    // assertSame(four, result);

    // retest on copy of map
    Map<String, String[]> newMap = new HashMap<String, String[]>();
    newMap.put(key, array);
    result = navigator.get(newMap, key + ".0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(one, result);
    result = navigator.get(newMap, key + ".1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(two, result);
  }

  @Test
  public void testErrors() {

    PojoPathNavigator navigator = createNavigator();
    FooOrBarFunction function = new FooOrBarFunction();
    DefaultPojoPathFunctionManager functionManager = new DefaultPojoPathFunctionManager();
    String functionName = "fooOrBar";
    functionManager.registerFunction(function, functionName);
    DefaultPojoPathContext defaultContext = new DefaultPojoPathContext();
    defaultContext.setAdditionalFunctionManager(functionManager);
    PojoPathContext context = defaultContext;
    // illegal path...
    try {
      navigator.get("foo", ".", PojoPathMode.RETURN_IF_NULL, context);
      fail("exception expected");
    } catch (IllegalPojoPathException e) {
    }
    // array index out of bounds...
    Object result = navigator.get(new String[0], "0.class", PojoPathMode.RETURN_IF_NULL, context);
    assertNull(result);
    try {
      navigator.get(new String[0], "0.class", PojoPathMode.FAIL_IF_NULL, context);
      fail("exception expected");
    } catch (IndexOutOfBoundsException e) {
    }
    // intermediate segment is null...
    result = navigator.get(new String[] { null }, "0", PojoPathMode.FAIL_IF_NULL, context);
    assertNull(result);
    try {
      navigator.get(new String[] { null }, "0.class", PojoPathMode.FAIL_IF_NULL, context);
      fail("exception expected");
    } catch (PojoPathSegmentIsNullException e) {
    }
    // undefined function...
    try {
      navigator.get("foo", "@undefined", PojoPathMode.RETURN_IF_NULL, context);
      fail("exception expected");
    } catch (PojoPathFunctionUndefinedException e) {
    }
    // path segment creation impossible...
    try {
      navigator.get(new String[0], "1", PojoPathMode.CREATE_IF_NULL, context);
      fail("exception expected");
    } catch (PojoPathCreationException e) {
    }
    // function with unsupported operation...
    try {
      navigator.set(new MyPojo(), "@" + functionName, PojoPathMode.CREATE_IF_NULL, context, "foo");
      fail("exception expected");
    } catch (PojoPathFunctionUnsupportedOperationException e) {
    }
  }

  public static class MyPojo {

    private Integer foo;

    private int bar;

    private boolean flag;

    private MyPojo parent;

    public Integer getFoo() {

      return this.foo;
    }

    public void setFoo(int foo) {

      this.foo = Integer.valueOf(foo);
    }

    public int getBar() {

      return this.bar;
    }

    public void setBar(int bar) {

      this.bar = bar;
    }

    public boolean hasFlag() {

      return this.flag;
    }

    public void setFlag() {

      this.flag = true;
    }

    public MyPojo getParent() {

      return this.parent;
    }

    public void setParent(MyPojo parent) {

      this.parent = parent;
    }

  }

  public static class FooOrBarFunction extends AbstractPojoPathFunction<MyPojo, Integer> {

    /**
     * {@inheritDoc}
     */
    public Integer get(MyPojo actual, String functionName, PojoPathContext context) {

      if (actual != null) {
        if (actual.hasFlag()) {
          return Integer.valueOf(actual.getBar());
        } else {
          return actual.getFoo();
        }
      }
      return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDeterministic() {

      // in this case it depends on the point of view whether this function
      // should be called deterministic or not. According to the official
      // definition it is deterministic...
      return false;
    }

  }

  public static class CollectionPojo {

    private Map<String, List<String[]>> map;

    private String string;

    public Map<String, List<String[]>> getMap() {

      return this.map;
    }

    public void setMap(Map<String, List<String[]>> map) {

      this.map = map;
    }

    public String getString() {

      return this.string;
    }

    public void setString(String string) {

      this.string = string;
    }
  }

}
