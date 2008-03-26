/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

import net.sf.mmm.util.GenericBean;

/**
 * This is the test-case for {@link CollectionUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CollectionUtilTest {

  protected CollectionUtil getCollectionUtil() {

    return CollectionUtil.getInstance();
  }

  @Test
  public void testCreate() {

    CollectionUtil util = getCollectionUtil();
    List<String> list = util.create(List.class);
    assertNotNull(list);
    assertTrue(list.isEmpty());
    ArrayList<String> arrayList = util.create(ArrayList.class);
    assertNotNull(arrayList);
    assertTrue(arrayList.isEmpty());
    Vector<String> vector = util.create(Vector.class);
    assertNotNull(vector);
    assertTrue(vector.isEmpty());
    Set<String> set = util.create(Set.class);
    assertNotNull(set);
    assertTrue(set.isEmpty());
    SortedSet<String> sortedSet = util.create(SortedSet.class);
    assertNotNull(sortedSet);
    assertTrue(sortedSet.isEmpty());
    Queue<String> queue = util.create(Queue.class);
    assertNotNull(queue);
    assertTrue(queue.isEmpty());
    BlockingQueue<String> blockingQueue = util.create(BlockingQueue.class);
    assertNotNull(blockingQueue);
    assertTrue(blockingQueue.isEmpty());
    Deque<String> deque = util.create(Deque.class);
    assertNotNull(deque);
    assertTrue(deque.isEmpty());
  }

  @Test
  public void testSet() {

    CollectionUtil util = getCollectionUtil();
    Object result;

    // test array...
    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String[] array = new String[3];
    util.set(array, 0, one);
    util.set(array, 1, two);
    util.set(array, 2, three);
    assertEquals(one, array[0]);
    assertEquals(two, array[1]);
    assertEquals(three, array[2]);
    try {
      util.set(array, 3, one, null, 1);
      fail("Exception expected!");
    } catch (Exception e) {
    }
    String[] arrayCopy;
    GenericBean<String[]> arrayBean = new GenericBean<String[]>();
    GenericBean arrayReceiver = (GenericBean) arrayBean;
    result = util.set(array, 3, one, arrayReceiver, 1);
    assertNull(result);
    arrayCopy = arrayBean.getValue();
    assertNotNull(arrayCopy);
    assertEquals(4, arrayCopy.length);
    assertEquals(one, arrayCopy[0]);
    assertEquals(two, arrayCopy[1]);
    assertEquals(three, arrayCopy[2]);
    assertEquals(one, arrayCopy[3]);
    arrayBean.setValue(null);
    result = util.set(array, 0, three, arrayReceiver, 0);
    assertNull(arrayBean.getValue());
    assertSame(one, result);
    assertEquals(three, array[0]);
    try {
      util.set(array, 4, one, arrayReceiver, 0);
      fail("Exception expected!");
    } catch (Exception e) {
    }

    // test list...
    List<String> list = new ArrayList<String>();
    result = util.set(list, 0, one);
    assertNull(result);
    assertEquals(1, list.size());
    result = util.set(list, 1, two);
    assertNull(result);
    assertEquals(2, list.size());
    result = util.set(list, 2, three);
    assertNull(result);
    assertEquals(3, list.size());
    assertEquals(one, list.get(0));
    assertEquals(two, list.get(1));
    assertEquals(three, list.get(2));
    result = util.set(list, 1, one);
    assertSame(two, result);
    assertSame(one, list.get(1));
    int maxGrowth = 128;
    list = new ArrayList<String>();
    result = util.set(list, maxGrowth - 1, one, null, maxGrowth);
    assertNull(result);
    assertEquals(maxGrowth, list.size());
    assertEquals(one, list.get(maxGrowth - 1));
    assertEquals(null, list.get(0));
    list = new ArrayList<String>();
    try {
      util.set(list, maxGrowth, one, null, maxGrowth);
      fail("Exception expected!");
    } catch (Exception e) {
    }
  }

  @Test
  public void testGetSize() {

    CollectionUtil util = getCollectionUtil();
    int size = 42;
    Integer[] array = new Integer[size];
    assertEquals(array.length, util.getSize(array));
    List<ClassLoader> list = new ArrayList<ClassLoader>();
    for (int i = 0; i < size; i++) {
      list.add(ClassLoader.getSystemClassLoader());
    }
    assertEquals(list.size(), util.getSize(list));
  }

  @Test
  public void testGet() {

    CollectionUtil util = getCollectionUtil();

    // test array...
    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String[] array = new String[] { one, two, three };
    for (int i = 0; i < array.length; i++) {
      assertEquals(array[i], util.get(array, i));
    }

    // test list...
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
      assertEquals(list.get(i), util.get(list, i));
    }
    // verify test-case ;)
    assertEquals(array.length, list.size());
  }

  @Test
  public void testAdd() {

    CollectionUtil util = getCollectionUtil();

    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";

    // test array...
    String[] array = new String[0];
    array = (String[]) util.add(array, one);
    assertEquals(1, array.length);
    assertEquals(one, array[0]);
    array = (String[]) util.add(array, two);
    assertEquals(2, array.length);
    assertEquals(one, array[0]);
    assertEquals(two, array[1]);
    array = (String[]) util.add(array, three);
    assertEquals(3, array.length);
    assertEquals(one, array[0]);
    assertEquals(two, array[1]);
    assertEquals(three, array[2]);

    // test list...
    List<String> list = new ArrayList<String>();
    util.add(list, one);
    assertEquals(1, list.size());
    util.add(list, two);
    assertEquals(2, list.size());
    util.add(list, three);
    assertEquals(3, list.size());
    assertEquals(one, list.get(0));
    assertEquals(two, list.get(1));
    assertEquals(three, list.get(2));
  }

  @Test
  public void testRemove() {

    CollectionUtil util = getCollectionUtil();

    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String four = "number four";
    String five = "fifth element";
    String notContained = "none";

    // test array...
    String[] array = new String[] { one, two, three, four, five };
    assertEquals(null, util.remove(array, notContained));
    String[] newArray = (String[]) util.remove(array, three);
    assertEquals(4, newArray.length);
    assertSame(one, newArray[0]);
    assertSame(two, newArray[1]);
    assertSame(four, newArray[2]);
    assertSame(five, newArray[3]);
    array = new String[] { one, new String(two), two, three, four, five };
    newArray = (String[]) util.remove(array, two);
    assertEquals(5, newArray.length);
    assertSame(one, newArray[0]);
    assertSame(two, newArray[1]);
    assertSame(three, newArray[2]);
    assertSame(four, newArray[3]);
    assertSame(five, newArray[4]);

    // test list...
    List<String> list = new ArrayList<String>();
    list.add(one);
    list.add(two);
    list.add(three);
    list.add(four);
    list.add(five);
    assertEquals(null, util.remove(list, notContained));
    Object result = util.remove(list, three);
    assertSame(list, result);
    assertEquals(4, list.size());
    assertSame(one, list.get(0));
    assertSame(two, list.get(1));
    assertSame(four, list.get(2));
    assertSame(five, list.get(3));

    list = new ArrayList<String>();
    list.add(one);
    list.add(new String(two));
    list.add(two);
    list.add(three);
    list.add(four);
    list.add(five);
    util.remove(list, two);
    assertEquals(5, list.size());
    assertSame(one, list.get(0));
    assertSame(two, list.get(1));
    assertSame(three, list.get(2));
    assertSame(four, list.get(3));
    assertSame(five, list.get(4));
  }

}
