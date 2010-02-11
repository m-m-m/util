/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.nls.api.IllegalCaseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link Comparator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class ComparatorTest {

  /**
   * This method checks the {@link Comparator#eval(Object, Object)} for the
   * given <code>comparator</code> and according {@link Comparator}s.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @param comparator - {@link Comparator#EQUAL} if <code>arg1 == arg2</code>,
   *        {@link Comparator#LESS_THAN} if <code>arg1 < arg2</code>,
   *        {@link Comparator#GREATER_THAN} if <code>arg1 > arg2</code> and
   *        {@link Comparator#NOT_EQUAL} otherwise.
   */
  protected void checkEvalTrue(Object arg1, Object arg2, Comparator comparator) {

    Assert.assertTrue(comparator.eval(arg1, arg2));
    switch (comparator) {
      case EQUAL:
        Assert.assertTrue(Comparator.GREATER_OR_EQUAL.eval(arg1, arg2));
        Assert.assertTrue(Comparator.LESS_OR_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.NOT_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.LESS_THAN.eval(arg1, arg2));
        Assert.assertFalse(Comparator.GREATER_THAN.eval(arg1, arg2));
        break;
      case NOT_EQUAL:
        Assert.assertFalse(Comparator.GREATER_OR_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.LESS_OR_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.EQUAL.eval(arg1, arg2));
        break;
      case GREATER_THAN:
        Assert.assertTrue(Comparator.GREATER_OR_EQUAL.eval(arg1, arg2));
        Assert.assertTrue(Comparator.NOT_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.LESS_OR_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.LESS_THAN.eval(arg1, arg2));
        Assert.assertFalse(Comparator.EQUAL.eval(arg1, arg2));
        break;
      case LESS_THAN:
        Assert.assertTrue(Comparator.LESS_OR_EQUAL.eval(arg1, arg2));
        Assert.assertTrue(Comparator.NOT_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.GREATER_OR_EQUAL.eval(arg1, arg2));
        Assert.assertFalse(Comparator.GREATER_THAN.eval(arg1, arg2));
        Assert.assertFalse(Comparator.EQUAL.eval(arg1, arg2));
        break;
      default :
        throw new IllegalCaseException(Comparator.class, comparator);
    }
  }

  /**
   * Tests {@link Comparator#eval(Object, Object)} for {@link Comparator#EQUAL}.
   */
  @Test
  public void testEqual() {

    Comparator comparator = Comparator.EQUAL;
    checkEvalTrue("foo", "foo", comparator);
    checkEvalTrue(Integer.valueOf(42), Integer.valueOf(42), comparator);
    checkEvalTrue(Short.valueOf((short) 1), Long.valueOf(1), comparator);
    checkEvalTrue(Double.valueOf(1.000), Long.valueOf(1), comparator);
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    checkEvalTrue(date, calendar, comparator);
    checkEvalTrue(calendar, date, comparator);
  }

  /**
   * Tests {@link Comparator#eval(Object, Object)} for
   * {@link Comparator#NOT_EQUAL}.
   */
  @Test
  public void testGreaterThan() {

    Comparator comparator = Comparator.GREATER_THAN;
    // lexicographical order...
    checkEvalTrue("zzz", "aaa", comparator);
    checkEvalTrue(Integer.valueOf(42), Integer.valueOf(0), comparator);
    checkEvalTrue(Short.valueOf((short) 2), Long.valueOf(1), comparator);
    checkEvalTrue(Double.valueOf(1.001), Long.valueOf(1), comparator);
  }

  /**
   * Tests {@link Comparator#eval(Object, Object)} for
   * {@link Comparator#NOT_EQUAL}.
   */
  @Test
  public void testLessThan() {

    Comparator comparator = Comparator.LESS_THAN;
    // lexicographical order...
    checkEvalTrue("a", "b", comparator);
    checkEvalTrue(Integer.valueOf(-42), Integer.valueOf(0), comparator);
    checkEvalTrue(Short.valueOf((short) 1), Long.valueOf(2), comparator);
    checkEvalTrue(Double.valueOf(0.999), Long.valueOf(1), comparator);
  }

  /**
   * Tests {@link Comparator#eval(Object, Object)} for
   * {@link Comparator#NOT_EQUAL}.
   */
  @Test
  public void testNotEqual() {

    Comparator comparator = Comparator.NOT_EQUAL;
    checkEvalTrue("fourtytwo", Integer.valueOf(42), comparator);
    checkEvalTrue(new Date(), Integer.valueOf(1), comparator);
  }
}
