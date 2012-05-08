/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.base.ConjunctionFilter;
import net.sf.mmm.util.lang.api.Conjunction;

/**
 * This is the test-case for {@link ConjunctionFilter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ConjunctionFilterTest {

  @Test
  public void testOr() {

    ConjunctionFilter<Integer> filter;

    // Test empty filter
    filter = new ConjunctionFilter<Integer>(Conjunction.OR);
    assertFalse(filter.accept(0));
    assertFalse(filter.accept(1));
    assertFalse(filter.accept(42));

    // Test single filter
    filter = new ConjunctionFilter<Integer>(Conjunction.OR, new IntegerFilter(0));
    assertTrue(filter.accept(0));
    assertFalse(filter.accept(1));
    assertFalse(filter.accept(42));

    // Test two filters
    filter = new ConjunctionFilter<Integer>(Conjunction.OR, new IntegerFilter(0), new IntegerFilter(1));
    assertTrue(filter.accept(0));
    assertTrue(filter.accept(1));
    assertFalse(filter.accept(42));
  }

  @Test
  public void testNor() {

    ConjunctionFilter<Integer> filter;

    // Test empty filter
    filter = new ConjunctionFilter<Integer>(Conjunction.NOR);
    assertTrue(filter.accept(0));
    assertTrue(filter.accept(1));
    assertTrue(filter.accept(42));

    // Test single filter
    filter = new ConjunctionFilter<Integer>(Conjunction.NOR, new IntegerFilter(0));
    assertFalse(filter.accept(0));
    assertTrue(filter.accept(1));
    assertTrue(filter.accept(42));

    // Test two filters
    filter = new ConjunctionFilter<Integer>(Conjunction.NOR, new IntegerFilter(0), new IntegerFilter(1));
    assertFalse(filter.accept(0));
    assertFalse(filter.accept(1));
    assertTrue(filter.accept(42));
  }

  @Test
  public void testAnd() {

    ConjunctionFilter<Integer> filter;

    // Test empty filter
    filter = new ConjunctionFilter<Integer>(Conjunction.AND);
    assertTrue(filter.accept(0));
    assertTrue(filter.accept(1));
    assertTrue(filter.accept(42));

    // Test single filter
    filter = new ConjunctionFilter<Integer>(Conjunction.AND, new IntegerFilter(0));
    assertTrue(filter.accept(0));
    assertFalse(filter.accept(1));
    assertFalse(filter.accept(42));

    // Test two filters
    filter = new ConjunctionFilter<Integer>(Conjunction.AND, new IntegerFilter(0), new IntegerFilter(1));
    assertFalse(filter.accept(0));
    assertFalse(filter.accept(1));
    assertFalse(filter.accept(42));
    filter = new ConjunctionFilter<Integer>(Conjunction.AND, new IntegerFilter(0), new IntegerFilter(0));
    assertTrue(filter.accept(0));
    assertFalse(filter.accept(1));
    assertFalse(filter.accept(42));
  }

  @Test
  public void testNand() {

    ConjunctionFilter<Integer> filter;

    // Test empty filter
    filter = new ConjunctionFilter<Integer>(Conjunction.NAND);
    assertFalse(filter.accept(0));
    assertFalse(filter.accept(1));
    assertFalse(filter.accept(42));

    // Test single filter
    filter = new ConjunctionFilter<Integer>(Conjunction.NAND, new IntegerFilter(0));
    assertFalse(filter.accept(0));
    assertTrue(filter.accept(1));
    assertTrue(filter.accept(42));

    // Test two filters
    filter = new ConjunctionFilter<Integer>(Conjunction.NAND, new IntegerFilter(0), new IntegerFilter(1));
    assertTrue(filter.accept(0));
    assertTrue(filter.accept(1));
    assertTrue(filter.accept(42));
    filter = new ConjunctionFilter<Integer>(Conjunction.NAND, new IntegerFilter(0), new IntegerFilter(0));
    assertFalse(filter.accept(0));
    assertTrue(filter.accept(1));
    assertTrue(filter.accept(42));
  }

  private static class IntegerFilter implements Filter<Integer> {

    private final int acceptValue;

    public IntegerFilter(int acceptValue) {

      super();
      this.acceptValue = acceptValue;
    }

    public boolean accept(Integer value) {

      if (value == null) {
        return false;
      }
      return (value.intValue() == this.acceptValue);
    }
  }

}
