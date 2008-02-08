/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    // test array...
    String one = "The first one!";
    String two = "Second...";
    String three = "Third and last.";
    String[] all = new String[3];
    util.set(all, 0, one);
    util.set(all, 1, two);
    util.set(all, 2, three);
    assertEquals(one, all[0]);
    assertEquals(two, all[1]);
    assertEquals(three, all[2]);

    // test list...
    List<String> list = new ArrayList<String>();
    util.set(list, 0, one);
    assertEquals(1, list.size());
    util.set(list, 1, two);
    assertEquals(2, list.size());
    util.set(list, 2, three);
    assertEquals(3, list.size());
    assertEquals(one, list.get(0));
    assertEquals(two, list.get(1));
    assertEquals(three, list.get(2));
    list = new ArrayList<String>();
    int maxGrowth = 128;
    util.set(list, maxGrowth - 1, one, maxGrowth);
    assertEquals(maxGrowth, list.size());
    assertEquals(one, list.get(maxGrowth - 1));
    assertEquals(null, list.get(0));
    list = new ArrayList<String>();
    try {
      util.set(list, maxGrowth, one, maxGrowth);
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

}
