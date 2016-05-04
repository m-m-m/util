/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * This is the test-case for {@link ArrayListFactory#INSTANCE}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ListFactoryTest {

  @Test
  public void testHashMap() {

    List<String> list = ArrayListFactory.INSTANCE.create();
    assertNotNull(list);
    assertEquals(ArrayList.class, list.getClass());
    assertTrue(list.isEmpty());
    list = ArrayListFactory.INSTANCE.create(42);
    // we can NOT easily check the capacity. Only chance would be to use
    // reflection. But we do not want to test the JDK here. Anyway it would be
    // 64 rather than 42.
    assertNotNull(list);
    assertEquals(ArrayList.class, list.getClass());
    assertTrue(list.isEmpty());
  }
}
