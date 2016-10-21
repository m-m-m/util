/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import net.sf.mmm.test.ObjectHelper;

/**
 * Test-case for {@link Range}.
 *
 * @author hohwille
 */
public class RangeTest extends Assertions {

  @Test
  public void testRangeWithLong() {

    Long min = 1L;
    Long max = 10L;
    Range<Long> range = new Range<>(min, max);
    assertThat(range.getMin()).isEqualTo(min);
    assertThat(range.getMax()).isEqualTo(max);
    assertThat(range.isContained(min)).isTrue();
    assertThat(range.isContained(max)).isTrue();
    assertThat(range.isContained(5L)).isTrue();
    assertThat(range.isContained(min - 1L)).isFalse();
    assertThat(range.isContained(max + 1L)).isFalse();
  }

  @Test
  public void testRangeWithLocalDate() {

    LocalDate min = LocalDate.of(2000, 1, 1);
    LocalDate max = LocalDate.of(2100, 12, 31);
    Range<LocalDate> range = new Range<>(min, max);
    assertThat(range.getMin()).isEqualTo(min);
    assertThat(range.getMax()).isEqualTo(max);
    assertThat(range.isContained(min)).isTrue();
    assertThat(range.isContained(max)).isTrue();
    assertThat(range.isContained(LocalDate.of(2000, 1, 2))).isTrue();
    assertThat(range.isContained(LocalDate.of(2100, 12, 30))).isTrue();
    assertThat(range.isContained(min.minusDays(1))).isFalse();
    assertThat(range.isContained(max.plusDays(1))).isFalse();
  }

  @Test
  public void testEqualsAndHashCode() {

    Range<LocalDateTime> rangeNull1 = new Range<>();
    Range<LocalDateTime> rangeNull2 = new Range<>();
    ObjectHelper.checkEqualsAndHashCode(rangeNull1, rangeNull2, true);

    LocalDate min = LocalDate.of(2000, 1, 1);
    LocalDate max = LocalDate.of(2100, 12, 31);
    Range<LocalDate> rangeMinMax1 = new Range<>(min, max);
    Range<LocalDate> rangeMinMax2 = new Range<>(min, max);
    ObjectHelper.checkEqualsAndHashCode(rangeMinMax1, rangeMinMax2, true);

    ObjectHelper.checkEqualsAndHashCode(rangeNull1, rangeMinMax1, false);
  }

  @Test
  public void testBuilder() {

    Range<LocalDateTime> range = new Range<>();
    assertThat(range.getMinimumValue()).isNull();
    assertThat(range.getMaximumValue()).isNull();
    assertThat(range.withMin(null)).isSameAs(range);
    assertThat(range.withMax(null)).isSameAs(range);
    LocalDateTime min = LocalDateTime.of(1999, 12, 31, 23, 59);
    Range<LocalDateTime> rangeWithMin = range.withMin(min);
    assertThat(rangeWithMin).isNotSameAs(range).isNotNull();
    assertThat(rangeWithMin.getMin()).isSameAs(min);
    LocalDateTime max = LocalDateTime.of(2000, 1, 1, 00, 00);
    Range<LocalDateTime> rangeWithMinAndMax = rangeWithMin.withMax(max);
    assertThat(rangeWithMinAndMax).isNotSameAs(rangeWithMin).isNotSameAs(range).isNotNull();
    assertThat(rangeWithMinAndMax.getMin()).isSameAs(min);
    assertThat(rangeWithMinAndMax.getMax()).isSameAs(max);
    LocalDateTime min2 = LocalDateTime.of(1998, 12, 31, 23, 59);
    Range<LocalDateTime> rangeWithMin2AndMax = rangeWithMinAndMax.withMin(min2);
    assertThat(rangeWithMin2AndMax).isNotSameAs(rangeWithMinAndMax).isNotSameAs(rangeWithMin).isNotSameAs(range).isNotNull();
    Range<LocalDateTime> range2 = rangeWithMin2AndMax.withMin(null).withMax(null);
    assertThat(range2).isNotSameAs(range).isEqualTo(range);
  }
}
