/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test of {@link CompareOperator}.
 */
public class CompareOperatorTest extends Assertions {

  /** Test of {@link CompareOperator#EQUAL}. */
  @Test
  public void testEqual() {

    // given
    CompareOperator op = CompareOperator.EQUAL;

    // when + then
    assertThat(op.getValue()).isEqualTo("==");
    assertThat(op.toString()).isEqualTo("equal to");

    verifyOperator(op, false, false, true);
  }

  /** Test of {@link CompareOperator#NOT_EQUAL}. */
  @Test
  public void testNotEqual() {

    // given
    CompareOperator op = CompareOperator.NOT_EQUAL;

    // when + then
    assertThat(op.getValue()).isEqualTo("!=");
    assertThat(op.toString()).isEqualTo("not equal to");

    verifyOperator(op, true, true, false);
  }

  /** Test of {@link CompareOperator#GREATER_THAN}. */
  @Test
  public void testGreaterThan() {

    // given
    CompareOperator op = CompareOperator.GREATER_THAN;

    // when + then
    assertThat(op.getValue()).isEqualTo(">");
    assertThat(op.toString()).isEqualTo("greater than");

    verifyOperator(op, false, true, false);
  }

  /** Test of {@link CompareOperator#GREATER_OR_EQUAL}. */
  @Test
  public void testGreaterOrEqual() {

    // given
    CompareOperator op = CompareOperator.GREATER_OR_EQUAL;

    // when + then
    assertThat(op.getValue()).isEqualTo(">=");
    assertThat(op.toString()).isEqualTo("greater or equal to");

    verifyOperator(op, false, true, true);
  }

  /** Test of {@link CompareOperator#LESS_THAN}. */
  @Test
  public void testLessThan() {

    // given
    CompareOperator op = CompareOperator.LESS_THAN;

    // when + then
    assertThat(op.getValue()).isEqualTo("<");
    assertThat(op.toString()).isEqualTo("less than");

    verifyOperator(op, true, false, false);
  }

  /** Test of {@link CompareOperator#LESS_OR_EQUAL}. */
  @Test
  public void testLessOrEqual() {

    // given
    CompareOperator op = CompareOperator.LESS_OR_EQUAL;

    // when + then
    assertThat(op.getValue()).isEqualTo("<=");
    assertThat(op.toString()).isEqualTo("less or equal to");

    verifyOperator(op, true, false, true);
  }

  private void verifyOperator(CompareOperator op, boolean acceptLess, boolean acceptGreater, boolean acceptEqual) {

    assertThat(op.eval(1, 0)).isEqualTo(acceptGreater);
    assertThat(op.eval(0, 1)).isEqualTo(acceptLess);
    assertThat(op.eval(1, 1)).isEqualTo(acceptEqual);
    assertThat(op.eval(Double.valueOf(1), Integer.valueOf(0))).isEqualTo(acceptGreater);
    assertThat(op.eval(Double.valueOf(0), Integer.valueOf(1))).isEqualTo(acceptLess);
    assertThat(op.eval(Double.valueOf(1), Integer.valueOf(1))).isEqualTo(acceptEqual);
    assertThat(op.eval(new BigInteger("12345678901234567890"), BigInteger.valueOf(Long.MAX_VALUE))).isEqualTo(acceptGreater);
    assertThat(op.eval(new BigDecimal("12345678901234567890"), BigInteger.valueOf(Long.MAX_VALUE))).isEqualTo(acceptGreater);
  }

}
