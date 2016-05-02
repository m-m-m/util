/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test-case of {@link Range}.
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

}
