/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * This is the test-case for {@link SetFactory#INSTANCE_HASH_SET}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class SetFactoryTest {

  @Test
  public void testHashMap() {

    Set<String> list = SetFactory.INSTANCE_HASH_SET.create();
    assertNotNull(list);
    assertEquals(HashSet.class, list.getClass());
    assertTrue(list.isEmpty());
    list = SetFactory.INSTANCE_HASH_SET.create(42);
    // we can NOT easily check the capacity. Only chance would be to use
    // reflection. But we do not want to test the JDK here. Anyway it would be
    // 64 rather than 42.
    assertNotNull(list);
    assertEquals(HashSet.class, list.getClass());
    assertTrue(list.isEmpty());
  }
}
