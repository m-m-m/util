/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

import net.sf.mmm.util.pojo.api.PojoFactory;

/**
 * This is the test-case for {@link PojoFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PojoFactoryTest {

  protected PojoFactory getPojoFactory() {

    GuessingPojoFactory factory = new GuessingPojoFactory();
    factory.initialize();
    return factory;
  }

  @Test
  public void testCollections() {

    PojoFactory factory = getPojoFactory();
    Class<? extends Collection>[] collectionTypes = new Class[] { Set.class, List.class, Queue.class,
    Collection.class, SortedSet.class };
    for (Class<? extends Collection> type : collectionTypes) {
      Collection instance = factory.newInstance(type);
      assertNotNull(instance);
      assertEquals(0, instance.size());
    }
  }

  public void testMap() {

    PojoFactory factory = getPojoFactory();
    Map map = factory.newInstance(Map.class);
    assertNotNull(map);
    assertEquals(0, map.size());
  }

  @Test
  public void testSimple() {

    PojoFactory factory = getPojoFactory();
    String string = factory.newInstance(String.class);
    assertNotNull(string);
    assertEquals(0, string.length());
  }

}
