/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Date;
import java.util.Locale;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.validation.api.ValidationFailure;

import org.junit.Test;

/**
 * This is the test-case for {@link ValidatorRange}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidatorRangeTest extends AbstractValidatorTest {

  /**
   * Tests {@link ValidatorRange} for ranges of {@link Integer}.
   */
  @Test
  public void testIntegers() {

    int min = 0;
    int max = 42;
    Integer minimum = Integer.valueOf(min);
    Integer maximum = Integer.valueOf(max);
    ValidatorRange<Integer> validator = new ValidatorRange<>(minimum, maximum);
    verifyPositiveValidation(validator, null);
    verifyPositiveValidation(validator, minimum);
    verifyPositiveValidation(validator, maximum);
    verifyPositiveValidation(validator, Integer.valueOf(min + 1));
    verifyPositiveValidation(validator, Integer.valueOf(max - 1));
    verifyOutOfRange(minimum, maximum, validator, Integer.valueOf(min - 1));
    verifyOutOfRange(minimum, maximum, validator, Integer.valueOf(max + 1));
  }

  /**
   * Tests {@link ValidatorRange} for ranges of {@link Date}.
   */
  @Test
  public void testDates() {

    Iso8601Util isoUtil = Iso8601UtilImpl.getInstance();
    Date min = isoUtil.parseDate("1999-12-31T23:59:59");
    Date max = isoUtil.parseDate("2000-01-01T00:00:01");
    ValidatorRange<Date> validator = new ValidatorRange<>(min, max);
    verifyPositiveValidation(validator, null);
    verifyPositiveValidation(validator, min);
    verifyPositiveValidation(validator, max);
    verifyPositiveValidation(validator, isoUtil.parseDate("2000-01-01T00:00:00"));

    verifyOutOfRange(min, max, validator, isoUtil.parseDate("2000-01-31T23:59:58"));
  }

  /**
   * Verifies that the given {@code valueOutOfRange} is out of range.
   *
   * @param <V> is the generic type of the value.
   * @param min is the minimum value.
   * @param max is the maximum value.
   * @param validator is the {@link ValidatorRange}.
   * @param valueOutOfRange is the value that is out of range.
   */
  protected <V extends Comparable<V>> void verifyOutOfRange(V min, V max, ValidatorRange<V> validator,
      V valueOutOfRange) {

    Locale locale = AbstractNlsMessage.LOCALE_ROOT;
    ValidationFailure failure = verifyNegativeValidation(
        validator,
        valueOutOfRange,
        "The value " + toString(valueOutOfRange, locale) + " needs to be in the range from "
            + toString(min, locale) + " to " + toString(max, locale) + ".");
    locale = Locale.GERMANY;
    assertEquals("Der Wert " + toString(valueOutOfRange, locale) + " muss im Bereich von " + toString(min, locale)
        + " bis " + toString(max, locale) + " liegen.", failure.getMessage(locale));
  }

}
