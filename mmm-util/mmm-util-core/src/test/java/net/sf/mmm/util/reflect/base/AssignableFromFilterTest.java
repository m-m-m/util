/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.sf.mmm.util.reflect.base.AssignableFromFilter;

/**
 * This is the test-case for the class {@link AssignableFromFilter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class AssignableFromFilterTest {

  @Test
  public void testFilter() {

    AssignableFromFilter filter = new AssignableFromFilter(Object.class);
    assertFalse(filter.accept(null));
    assertTrue(filter.accept(Object.class));
    assertTrue(filter.accept(String.class));
    assertTrue(filter.accept(Integer.class));
    assertTrue(filter.accept(Long.class));
    assertTrue(filter.accept(Double.class));
    assertTrue(filter.accept(Thread.class));
    filter = new AssignableFromFilter(Number.class);
    assertFalse(filter.accept(Object.class));
    assertFalse(filter.accept(String.class));
    assertTrue(filter.accept(Integer.class));
    assertTrue(filter.accept(Long.class));
    assertTrue(filter.accept(Double.class));
    assertFalse(filter.accept(Thread.class));
  }

}
