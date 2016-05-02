/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.base;

import net.sf.mmm.util.date.api.DurationUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link DurationUtil}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class DurationUtilTest extends Assert {

  /**
   * @return the {@link DurationUtil} instance to test.
   */
  public DurationUtil getDurationUtil() {

    return DurationUtilImpl.getInstance();
  }

  /**
   * Tests {@link DurationUtil#formatSeconds(long)}.
   */
  @Test
  public void testFormatSeconds() {

    DurationUtil durationUtil = getDurationUtil();
    assertEquals("1", durationUtil.formatSeconds(1));
    assertEquals("1:01", durationUtil.formatSeconds(61));
    assertEquals("1:59:01", durationUtil.formatSeconds(7141));
    assertEquals("14D06:56:07", durationUtil.formatSeconds(1234567));
  }

  /**
   * Tests {@link DurationUtil#formatMilliseconds(long)}.
   */
  @Test
  public void testFormatMilliseconds() {

    DurationUtil durationUtil = getDurationUtil();
    assertEquals("0.001", durationUtil.formatMilliseconds(1));
    assertEquals("1:01", durationUtil.formatMilliseconds(61000));
    assertEquals("1:59:01.123", durationUtil.formatMilliseconds(7141123));
    assertEquals("14D06:56:07.890", durationUtil.formatMilliseconds(1234567890));
  }

  /**
   * Tests {@link DurationUtil#formatNanoseconds(long)}.
   */
  @Test
  public void testFormatNanoseconds() {

    DurationUtil durationUtil = getDurationUtil();
    assertEquals("0.000000001", durationUtil.formatNanoseconds(1));
    assertEquals("1:01", durationUtil.formatNanoseconds(61000000000L));
    assertEquals("1:59:01.123456789", durationUtil.formatNanoseconds(7141123456789L));
    assertEquals("14D06:56:07.890123456", durationUtil.formatNanoseconds(1234567890123456L));
  }

}
