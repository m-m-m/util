/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Calendar;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.util.exception.api.IllegalCaseException;

/**
 * Test of {@link CompareOperator}.
 */
public class ComparatorTest extends Assertions {

  /**
   * This method checks the {@link CompareOperator#eval(Object, Object)} for the given {@code comparator} and according
   * {@link CompareOperator}s.
   *
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @param comparator - {@link CompareOperator#EQUAL} if {@code arg1 == arg2}, {@link CompareOperator#LESS_THAN} if
   *        {@code arg1 < arg2}, {@link CompareOperator#GREATER_THAN} if {@code arg1 > arg2} and
   *        {@link CompareOperator#NOT_EQUAL} otherwise.
   */
  protected void checkEvalTrue(Object arg1, Object arg2, CompareOperator comparator) {

    assertThat(comparator.eval(arg1, arg2)).isTrue();
    switch (comparator) {
      case EQUAL:
        assertThat(CompareOperator.GREATER_OR_EQUAL.eval(arg1, arg2)).isTrue();
        assertThat(CompareOperator.LESS_OR_EQUAL.eval(arg1, arg2)).isTrue();
        assertThat(CompareOperator.NOT_EQUAL.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.LESS_THAN.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.GREATER_THAN.eval(arg1, arg2)).isFalse();
        break;
      case NOT_EQUAL:
        assertThat(CompareOperator.GREATER_OR_EQUAL.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.LESS_OR_EQUAL.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.EQUAL.eval(arg1, arg2)).isFalse();
        break;
      case GREATER_THAN:
        assertThat(CompareOperator.GREATER_OR_EQUAL.eval(arg1, arg2)).isTrue();
        assertThat(CompareOperator.NOT_EQUAL.eval(arg1, arg2)).isTrue();
        assertThat(CompareOperator.LESS_OR_EQUAL.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.LESS_THAN.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.EQUAL.eval(arg1, arg2)).isFalse();
        break;
      case LESS_THAN:
        assertThat(CompareOperator.LESS_OR_EQUAL.eval(arg1, arg2)).isTrue();
        assertThat(CompareOperator.NOT_EQUAL.eval(arg1, arg2)).isTrue();
        assertThat(CompareOperator.GREATER_OR_EQUAL.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.GREATER_THAN.eval(arg1, arg2)).isFalse();
        assertThat(CompareOperator.EQUAL.eval(arg1, arg2)).isFalse();
        break;
      default :
        throw new IllegalCaseException(CompareOperator.class, comparator);
    }
  }

  /**
   * Tests {@link CompareOperator#eval(Object, Object)} for {@link CompareOperator#EQUAL}.
   */
  @Test
  public void testEqual() {

    CompareOperator comparator = CompareOperator.EQUAL;
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
   * Tests {@link CompareOperator#eval(Object, Object)} for {@link CompareOperator#NOT_EQUAL}.
   */
  @Test
  public void testGreaterThan() {

    CompareOperator comparator = CompareOperator.GREATER_THAN;
    // lexicographical order...
    checkEvalTrue("zzz", "aaa", comparator);
    checkEvalTrue(Integer.valueOf(42), Integer.valueOf(0), comparator);
    checkEvalTrue(Short.valueOf((short) 2), Long.valueOf(1), comparator);
    checkEvalTrue(Double.valueOf(1.001), Long.valueOf(1), comparator);
  }

  /**
   * Tests {@link CompareOperator#eval(Object, Object)} for {@link CompareOperator#NOT_EQUAL}.
   */
  @Test
  public void testLessThan() {

    CompareOperator comparator = CompareOperator.LESS_THAN;
    // lexicographical order...
    checkEvalTrue("a", "b", comparator);
    checkEvalTrue(Integer.valueOf(-42), Integer.valueOf(0), comparator);
    checkEvalTrue(Short.valueOf((short) 1), Long.valueOf(2), comparator);
    checkEvalTrue(Double.valueOf(0.999), Long.valueOf(1), comparator);
  }

  /**
   * Tests {@link CompareOperator#eval(Object, Object)} for {@link CompareOperator#NOT_EQUAL}.
   */
  @Test
  public void testNotEqual() {

    CompareOperator comparator = CompareOperator.NOT_EQUAL;
    checkEvalTrue("fourtytwo", Integer.valueOf(42), comparator);
    checkEvalTrue(new Date(), Integer.valueOf(1), comparator);
  }
}
