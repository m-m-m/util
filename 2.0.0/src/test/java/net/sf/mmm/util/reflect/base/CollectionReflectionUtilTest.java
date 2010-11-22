/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;

import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link CollectionReflectionUtilImpl}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class CollectionReflectionUtilTest {

  protected CollectionReflectionUtil getCollectionReflectionUtil() {

    return CollectionReflectionUtilImpl.getInstance();
  }

  @Test
  public void testCreate() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();
    List<String> list = util.create(List.class);
    Assert.assertNotNull(list);
    Assert.assertTrue(list.isEmpty());
    ArrayList<String> arrayList = util.create(ArrayList.class);
    Assert.assertNotNull(arrayList);
    Assert.assertTrue(arrayList.isEmpty());
    Vector<String> vector = util.create(Vector.class);
    Assert.assertNotNull(vector);
    Assert.assertTrue(vector.isEmpty());
    Set<String> set = util.create(Set.class);
    Assert.assertNotNull(set);
    Assert.assertTrue(set.isEmpty());
    SortedSet<String> sortedSet = util.create(SortedSet.class);
    Assert.assertNotNull(sortedSet);
    Assert.assertTrue(sortedSet.isEmpty());
    Queue<String> queue = util.create(Queue.class);
    Assert.assertNotNull(queue);
    Assert.assertTrue(queue.isEmpty());
    BlockingQueue<String> blockingQueue = util.create(BlockingQueue.class);
    Assert.assertNotNull(blockingQueue);
    Assert.assertTrue(blockingQueue.isEmpty());
    Deque<String> deque = util.create(Deque.class);
    Assert.assertNotNull(deque);
    Assert.assertTrue(deque.isEmpty());
  }

  @Test
  public void testSet() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();
    Object result;

    // test array...
    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String[] array = new String[3];
    util.set(array, 0, one);
    util.set(array, 1, two);
    util.set(array, 2, three);
    Assert.assertEquals(one, array[0]);
    Assert.assertEquals(two, array[1]);
    Assert.assertEquals(three, array[2]);
    try {
      util.set(array, 3, one, null, 1);
      Assert.fail("Exception expected!");
    } catch (Exception e) {
    }
    String[] arrayCopy;
    GenericBean<String[]> arrayBean = new GenericBean<String[]>();
    GenericBean arrayReceiver = (GenericBean) arrayBean;
    result = util.set(array, 3, one, arrayReceiver, 1);
    Assert.assertNull(result);
    arrayCopy = arrayBean.getValue();
    Assert.assertNotNull(arrayCopy);
    Assert.assertEquals(4, arrayCopy.length);
    Assert.assertEquals(one, arrayCopy[0]);
    Assert.assertEquals(two, arrayCopy[1]);
    Assert.assertEquals(three, arrayCopy[2]);
    Assert.assertEquals(one, arrayCopy[3]);
    arrayBean.setValue(null);
    result = util.set(array, 0, three, arrayReceiver, 0);
    Assert.assertNull(arrayBean.getValue());
    Assert.assertSame(one, result);
    Assert.assertEquals(three, array[0]);
    try {
      util.set(array, 4, one, arrayReceiver, 0);
      Assert.fail("Exception expected!");
    } catch (Exception e) {
    }

    // test list...
    List<String> list = new ArrayList<String>();
    result = util.set(list, 0, one);
    Assert.assertNull(result);
    Assert.assertEquals(1, list.size());
    result = util.set(list, 1, two);
    Assert.assertNull(result);
    Assert.assertEquals(2, list.size());
    result = util.set(list, 2, three);
    Assert.assertNull(result);
    Assert.assertEquals(3, list.size());
    Assert.assertEquals(one, list.get(0));
    Assert.assertEquals(two, list.get(1));
    Assert.assertEquals(three, list.get(2));
    result = util.set(list, 1, one);
    Assert.assertSame(two, result);
    Assert.assertSame(one, list.get(1));
    int maxGrowth = 128;
    list = new ArrayList<String>();
    result = util.set(list, maxGrowth - 1, one, null, maxGrowth);
    Assert.assertNull(result);
    Assert.assertEquals(maxGrowth, list.size());
    Assert.assertEquals(one, list.get(maxGrowth - 1));
    Assert.assertEquals(null, list.get(0));
    list = new ArrayList<String>();
    try {
      util.set(list, maxGrowth, one, null, maxGrowth);
      Assert.fail("Exception expected!");
    } catch (Exception e) {
    }
  }

  @Test
  public void testGetSize() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();
    int size = 42;
    Integer[] array = new Integer[size];
    Assert.assertEquals(array.length, util.getSize(array));
    List<ClassLoader> list = new ArrayList<ClassLoader>();
    for (int i = 0; i < size; i++) {
      list.add(ClassLoader.getSystemClassLoader());
    }
    Assert.assertEquals(list.size(), util.getSize(list));
  }

  @Test
  public void testGet() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();

    // test array...
    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String[] array = new String[] { one, two, three };
    for (int i = 0; i < array.length; i++) {
      Assert.assertEquals(array[i], util.get(array, i));
    }

    // test list...
    List<String> list = new ArrayList<String>();
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
      Assert.assertEquals(list.get(i), util.get(list, i));
    }
    // verify test-case ;)
    Assert.assertEquals(array.length, list.size());
  }

  @Test
  public void testAdd() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();

    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";

    // test array...
    String[] array = new String[0];
    array = (String[]) util.add(array, one);
    Assert.assertEquals(1, array.length);
    Assert.assertEquals(one, array[0]);
    array = (String[]) util.add(array, two);
    Assert.assertEquals(2, array.length);
    Assert.assertEquals(one, array[0]);
    Assert.assertEquals(two, array[1]);
    array = (String[]) util.add(array, three);
    Assert.assertEquals(3, array.length);
    Assert.assertEquals(one, array[0]);
    Assert.assertEquals(two, array[1]);
    Assert.assertEquals(three, array[2]);

    // test list...
    List<String> list = new ArrayList<String>();
    util.add(list, one);
    Assert.assertEquals(1, list.size());
    util.add(list, two);
    Assert.assertEquals(2, list.size());
    util.add(list, three);
    Assert.assertEquals(3, list.size());
    Assert.assertEquals(one, list.get(0));
    Assert.assertEquals(two, list.get(1));
    Assert.assertEquals(three, list.get(2));
  }

  @Test
  public void testRemove() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();

    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String four = "number four";
    String five = "fifth element";
    String notContained = "none";

    // test array...
    String[] array = new String[] { one, two, three, four, five };
    Assert.assertEquals(null, util.remove(array, notContained));
    String[] newArray = (String[]) util.remove(array, three);
    Assert.assertEquals(4, newArray.length);
    Assert.assertSame(one, newArray[0]);
    Assert.assertSame(two, newArray[1]);
    Assert.assertSame(four, newArray[2]);
    Assert.assertSame(five, newArray[3]);
    array = new String[] { one, new String(two), two, three, four, five };
    newArray = (String[]) util.remove(array, two);
    Assert.assertEquals(5, newArray.length);
    Assert.assertSame(one, newArray[0]);
    Assert.assertSame(two, newArray[1]);
    Assert.assertSame(three, newArray[2]);
    Assert.assertSame(four, newArray[3]);
    Assert.assertSame(five, newArray[4]);

    // test list...
    List<String> list = new ArrayList<String>();
    list.add(one);
    list.add(two);
    list.add(three);
    list.add(four);
    list.add(five);
    Assert.assertEquals(null, util.remove(list, notContained));
    Object result = util.remove(list, three);
    Assert.assertSame(list, result);
    Assert.assertEquals(4, list.size());
    Assert.assertSame(one, list.get(0));
    Assert.assertSame(two, list.get(1));
    Assert.assertSame(four, list.get(2));
    Assert.assertSame(five, list.get(3));

    list = new ArrayList<String>();
    list.add(one);
    list.add(new String(two));
    list.add(two);
    list.add(three);
    list.add(four);
    list.add(five);
    util.remove(list, two);
    Assert.assertEquals(5, list.size());
    Assert.assertSame(one, list.get(0));
    Assert.assertSame(two, list.get(1));
    Assert.assertSame(three, list.get(2));
    Assert.assertSame(four, list.get(3));
    Assert.assertSame(five, list.get(4));
  }

  @Test
  public void testToArray() {

    CollectionReflectionUtil util = getCollectionReflectionUtil();

    List<Integer> numbers = new ArrayList<Integer>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
    numbers.add(4);
    numbers.add(5);

    // test list to object array...
    Integer[] array = util.toArrayTyped(numbers, Integer.class);
    Assert.assertEquals(numbers.size(), array.length);
    for (int i = 0; i < array.length; i++) {
      Assert.assertSame(numbers.get(i), array[i]);
    }

    // test list to primitive array...
    int[] primitiveArray = (int[]) util.toArray(numbers, int.class);
    Assert.assertEquals(numbers.size(), array.length);
    for (int i = 0; i < array.length; i++) {
      Assert.assertEquals(numbers.get(i).intValue(), primitiveArray[i]);
    }

    // test collection to array...
    Set<Integer> set = new HashSet<Integer>(numbers);
    array = util.toArrayTyped(set, Integer.class);
    Assert.assertEquals(numbers.size(), array.length);
    Set<Integer> set2 = new HashSet<Integer>();
    for (Integer i : array) {
      set2.add(i);
    }
    Assert.assertEquals(set, set2);
  }

}
