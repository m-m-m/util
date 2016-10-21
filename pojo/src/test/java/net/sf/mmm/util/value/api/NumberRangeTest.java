/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test-case of {@link NumberRange}.
 *
 * @author hohwille
 */
public class NumberRangeTest extends Assertions {

  @Test
  public void testRangeWithLong() {

    Double min = 1.1;
    Long max = 10L;
    NumberRange range = new NumberRange(min, max);
    assertThat(range.getMin()).isEqualTo(min);
    assertThat(range.getMax()).isEqualTo(max);
    assertThat(range.isContained(min)).isTrue();
    assertThat(range.isContained(max)).isTrue();
    assertThat(range.isContained(5)).isTrue();
    assertThat(range.isContained(min - 1L)).isFalse();
    assertThat(range.isContained(max + 1L)).isFalse();
  }

  @Test
  public void testBuilder() {

    NumberRange range = new NumberRange();
    assertThat(range.getMinimumValue()).isNull();
    assertThat(range.getMaximumValue()).isNull();
    assertThat(range.withMin(null)).isSameAs(range);
    assertThat(range.withMax(null)).isSameAs(range);
    Double min = Double.valueOf(-5.8);
    NumberRange rangeWithMin = range.withMin(min);
    assertThat(rangeWithMin).isNotSameAs(range).isNotNull();
    assertThat(rangeWithMin.getMin()).isSameAs(min);
    Long max = Long.valueOf(42000);
    NumberRange rangeWithMinAndMax = rangeWithMin.withMax(max);
    assertThat(rangeWithMinAndMax).isNotSameAs(rangeWithMin).isNotSameAs(range).isNotNull();
    assertThat(rangeWithMinAndMax.getMin()).isSameAs(min);
    assertThat(rangeWithMinAndMax.getMax()).isSameAs(max);
    Integer min2 = Integer.valueOf(-42);
    NumberRange rangeWithMin2AndMax = rangeWithMinAndMax.withMin(min2);
    assertThat(rangeWithMin2AndMax).isNotSameAs(rangeWithMinAndMax).isNotSameAs(rangeWithMin).isNotSameAs(range).isNotNull();
  }
}
