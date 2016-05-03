/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;
import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.pojo.path.base.AbstractPojoPathFunction;
import net.sf.mmm.util.pojo.path.base.DefaultPojoPathContext;
import net.sf.mmm.util.pojo.path.base.DefaultPojoPathFunctionManager;
import net.sf.mmm.util.reflect.api.GenericType;

import org.junit.Test;

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
    functionManager.initialize();
    // it is bad style to change the context on the run...
    DefaultPojoPathContext defaultContext = new DefaultPojoPathContext();
    defaultContext.setAdditionalFunctionManager(functionManager);
    context = defaultContext;
    result = navigator.get(myPojo, PojoPathFunction.FUNCTION_NAME_PREFIX + functionName, PojoPathMode.RETURN_IF_NULL,
        context);
    assertEquals(foo, result);
    Integer bar = Integer.valueOf(84);
    myPojo.setBar(bar.intValue());
    myPojo.setFlag();
    result = navigator.get(myPojo, PojoPathFunction.FUNCTION_NAME_PREFIX + functionName, PojoPathMode.RETURN_IF_NULL,
        context);
    assertEquals(bar, result);
  }

  @Test
  public void testGetTree() {

    PojoPathNavigator navigator = createNavigator();
    FooOrBarFunction function = new FooOrBarFunction();
    DefaultPojoPathFunctionManager functionManager = new DefaultPojoPathFunctionManager();
    String functionName = "fooOrBar";
    functionManager.registerFunction(function, functionName);
    functionManager.initialize();
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
    result = navigator.get(map, key + ".0.parent.parent.@" + functionName, PojoPathMode.RETURN_IF_NULL, context);
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
    String path = "map." + key + "." + listIndex + "." + arrayIndex;
    result = navigator.get(pojo, path, PojoPathMode.CREATE_IF_NULL, context);
    assertEquals("", result);
    String value = pojo.getMap().get(key).get(listIndex)[arrayIndex];
    assertSame(value, result);
    assertEquals(2, pojo.getMap().get(key).size());
    // rerun with different indices...
    listIndex = 2;
    arrayIndex = 1;
    result = navigator.get(pojo, "map." + key + "." + listIndex + "." + arrayIndex, PojoPathMode.CREATE_IF_NULL,
        context);
    assertEquals("", result);
    value = pojo.getMap().get(key).get(listIndex)[arrayIndex];
    assertSame(value, result);
    assertEquals(3, pojo.getMap().get(key).size());
  }

  @Test
  public void testCollection2List() {

    PojoPathNavigator navigator = createNavigator();
    DefaultPojoPathContext context = new DefaultPojoPathContext();

    CollectionPojo pojo = new CollectionPojo();
    Set<String> set = new HashSet<String>();
    pojo.setSet(set);
    String foo = "foo";
    set.add(foo);
    Object result;
    // test get...
    result = navigator.get(pojo, "set.0", PojoPathMode.FAIL_IF_NULL, context);
    assertSame(foo, result);

    // perform set...
    String bar = "bar";
    navigator.set(pojo, "set.1", PojoPathMode.FAIL_IF_NULL, context, bar);
    assertEquals(2, set.size());
    assertTrue(set.contains(foo));
    assertTrue(set.contains(bar));
    // perform set with override...
    String newFoo = "newFoo";
    navigator.set(pojo, "set.0", PojoPathMode.FAIL_IF_NULL, context, newFoo);
    assertEquals(2, set.size());
    assertFalse(set.contains(foo));
    assertTrue(set.contains(newFoo));
    assertTrue(set.contains(bar));

    // conversion only works if caching is active...
    context.setCache(null);
    try {
      navigator.get(pojo, "set.0", PojoPathMode.FAIL_IF_NULL, context);
      fail("Exception expected");
    } catch (PojoPathException e) {
    }
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
    String functionUndefined = "undefined";
    try {
      navigator.get("foo", "@" + functionUndefined, PojoPathMode.RETURN_IF_NULL, context);
      fail("exception expected");
    } catch (ObjectNotFoundException e) {
      assertTrue(e.getMessage().contains(functionUndefined));
    }
    // path segment creation impossible...
    try {
      navigator.get(new String[0], "1", PojoPathMode.CREATE_IF_NULL, context);
      fail("exception expected");
    } catch (PojoPathCreationException e) {
    }
    // function with unsupported operation...
    try {
      navigator.set(new MyPojo(), "@" + functionName, PojoPathMode.CREATE_IF_NULL, context, Integer.valueOf(0));
      fail("exception expected");
    } catch (NlsUnsupportedOperationException e) {
    }
  }

  @Test
  public void testFunction() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    Object value;
    Integer magic = Integer.valueOf(42);
    value = navigator.get(magic, "@toString", PojoPathMode.RETURN_IF_NULL, context);
    assertEquals("42", value);

    FooOrBarFunction function = new FooOrBarFunction();
    DefaultPojoPathFunctionManager functionManager = new DefaultPojoPathFunctionManager();
    String functionName = "fooOrBar";
    functionManager.registerFunction(function, functionName);
    functionManager.initialize();
    DefaultPojoPathContext defaultContext = new DefaultPojoPathContext();
    defaultContext.setAdditionalFunctionManager(functionManager);
    context = defaultContext;

  }

  @Test
  public void testConverter() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    Object value;
    // test using pojo
    MyPojo myPojo = new MyPojo();
    String foo = "42";
    navigator.set(myPojo, "foo", PojoPathMode.RETURN_IF_NULL, context, foo);
    value = navigator.get(myPojo, "foo", PojoPathMode.RETURN_IF_NULL, context);
    assertEquals(Integer.valueOf(foo), value);

    FooFunction function = new FooFunction();
    DefaultPojoPathFunctionManager functionManager = new DefaultPojoPathFunctionManager();
    String functionName = "foo";
    functionManager.registerFunction(function, functionName);
    functionManager.initialize();
    DefaultPojoPathContext defaultContext = new DefaultPojoPathContext();
    defaultContext.setAdditionalFunctionManager(functionManager);
    context = defaultContext;
    foo = "43";
    value = navigator.set(myPojo, "@foo", PojoPathMode.RETURN_IF_NULL, context, foo);
    Assert.assertEquals(Integer.valueOf(foo), myPojo.getFoo());

    CollectionPojo collectionPojo = new CollectionPojo();
    Map<Integer, List<String[]>> map = new HashMap<Integer, List<String[]>>();
    navigator.set(collectionPojo, "map", PojoPathMode.RETURN_IF_NULL, context, map);
    Assert.assertSame(map, collectionPojo.getMap());
    navigator.set(collectionPojo, "map.42", PojoPathMode.RETURN_IF_NULL, context, "<{[12,13]}>,24");
    List<String[]> subValue = map.get(Integer.valueOf(42));
    Assert.assertNotNull(subValue);
    Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(subValue.size()));
    String[] firstArray = subValue.get(0);
    Assert.assertEquals(Integer.valueOf(2), Integer.valueOf(firstArray.length));
    Assert.assertEquals("12", firstArray[0]);
    Assert.assertEquals("13", firstArray[1]);
    String[] secondArray = subValue.get(1);
    Assert.assertEquals(Integer.valueOf(1), Integer.valueOf(secondArray.length));
    Assert.assertEquals("24", secondArray[0]);
  }

  @Test
  public void testGetType() {

    PojoPathNavigator navigator = createNavigator();
    PojoPathContext context = new DefaultPojoPathContext();

    GenericType result;

    result = navigator.getType(Object.class, "class", true, context);
    assertEquals(Class.class, result.getRetrievalClass());

    result = navigator.getType(Object.class, "class.name", true, context);
    assertEquals(String.class, result.getRetrievalClass());

    result = navigator.getType(CollectionPojo.class, "map.key.0.1", true, context);
    assertEquals(String.class, result.getRetrievalClass());

    result = navigator.getType(CollectionPojo.class, "list.0.foo.bar", false, context);
    assertNull("" + result, result);

    try {
      result = navigator.getType(CollectionPojo.class, "list.0.foo.bar", true, context);
      fail("Exception expected");
    } catch (PojoPathUnsafeException e) {
      // okay
    }
  }

  public static class MyPojo {

    private Integer foo;

    private int bar;

    private boolean flag;

    private MyPojo parent;

    private Date date;

    private Calendar calendar;

    private String string;

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

    public Date getDate() {

      return this.date;
    }

    public void setDate(Date date) {

      this.date = date;
    }

    public Calendar getCalendar() {

      return this.calendar;
    }

    public void setCalendar(Calendar calendar) {

      this.calendar = calendar;
    }

    public String getString() {

      return this.string;
    }

    public void setString(String string) {

      this.string = string;
    }

  }

  /**
   * A {@link PojoPathFunction} that returns either foo or bar and is used for testing.
   */
  public static class FooFunction extends AbstractPojoPathFunction<MyPojo, Integer> {

    public Class<MyPojo> getInputClass() {

      return MyPojo.class;
    }

    public Class<Integer> getValueClass() {

      return Integer.class;
    }

    public Integer get(MyPojo actual, String functionName, PojoPathContext context) {

      if (actual != null) {
        actual.getFoo();
      }
      return null;
    }

    @Override
    public Integer set(MyPojo actual, String functionName, Integer value, PojoPathContext context) {

      Integer old = actual.getFoo();
      actual.setFoo(value);
      return old;
    }

  }

  /**
   * A {@link PojoPathFunction} that returns either foo or bar and is used for testing.
   */
  public static class FooOrBarFunction extends AbstractPojoPathFunction<MyPojo, Integer> {

    public Class<MyPojo> getInputClass() {

      return MyPojo.class;
    }

    public Class<Integer> getValueClass() {

      return Integer.class;
    }

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

    public boolean isDeterministic() {

      // in this case it depends on the point of view whether this function
      // should be called deterministic or not. According to the official
      // definition it is deterministic...
      return false;
    }

  }

  public static class CollectionPojo {

    private Map<Integer, List<String[]>> map;

    private String string;

    private Set<String> set;

    private List<Object> list;

    public Map<Integer, List<String[]>> getMap() {

      return this.map;
    }

    public void setMap(Map<Integer, List<String[]>> map) {

      this.map = map;
    }

    public String getString() {

      return this.string;
    }

    public void setString(String string) {

      this.string = string;
    }

    public Set<String> getSet() {

      return this.set;
    }

    public void setSet(Set<String> set) {

      this.set = set;
    }

    public List<Object> getList() {

      return this.list;
    }

    public void setList(List<Object> list) {

      this.list = list;
    }

  }

}
