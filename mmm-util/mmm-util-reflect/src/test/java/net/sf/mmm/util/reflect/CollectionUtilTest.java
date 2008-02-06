/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    util.set(list, 127, one, 128);
    assertEquals(128, list.size());

  }

}
