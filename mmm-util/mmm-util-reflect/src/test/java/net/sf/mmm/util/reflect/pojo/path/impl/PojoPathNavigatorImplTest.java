/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sf.mmm.util.reflect.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathNavigator;
import net.sf.mmm.util.reflect.pojo.path.base.DefaultPojoPathContext;

/**
 * This is the test-case for {@link PojoPathNavigatorImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathNavigatorImplTest {

  protected PojoPathNavigator createNavigator() {

    PojoPathNavigatorImpl navigator = new PojoPathNavigatorImpl();
    navigator.initialize();
    return navigator;
  }

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

    // test using list
    List<String> list = new ArrayList<String>();
    // test get on empty list
    result = navigator.get(list, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertNull(result);
    // test get for value in list
    String string = "string";
    list.add(string);
    result = navigator.get(list, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(string, result);
    String foo = "foo";
    list.add(foo);
    result = navigator.get(list, "1", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(foo, result);
    list.remove(0);
    // this works with active cache because the hashCode of the list changed
    result = navigator.get(list, "0", PojoPathMode.RETURN_IF_NULL, context);
    assertSame(foo, result);

    assertEquals("", "");
    assertTrue(true);
  }
}
