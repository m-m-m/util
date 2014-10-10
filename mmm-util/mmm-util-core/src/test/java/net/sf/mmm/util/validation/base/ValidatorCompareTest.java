/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.lang.api.CompareOperator;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.validation.api.ValidationFailure;

import org.junit.Test;

/**
 * This is the test-case for {@link ValidatorCompare}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidatorCompareTest extends AbstractValidatorTest {

  /**
   * Tests {@link ValidatorCompare} for various comparisons of {@link Integer}s.
   */
  @Test
  public void testIntegers() {

    ValidatorCompare<Integer> validator = new ValidatorCompare<Integer>(CompareOperator.LESS_THAN, Integer.valueOf(42));
    verifyPositiveValidation(validator, null);
    verifyPositiveValidation(validator, Integer.valueOf(0));
    verifyPositiveValidation(validator, Integer.valueOf(41));
    ValidationFailure failure = verifyNegativeValidation(validator, Integer.valueOf(42),
        "The value (42) needs to be less than \"42\"!");
    assertEquals("Der Wert (42) muss kleiner als \"42\" sein!", failure.getMessage(Locale.GERMANY));
  }

  /**
   * Tests {@link ValidatorCompare} for various comparisons of {@link Date}s.
   */
  @Test
  public void testDates() {

    Iso8601Util isoUtil = Iso8601UtilImpl.getInstance();
    Date date = isoUtil.parseDate("2000-01-31T23:59:59");
    ValidatorCompare<Date> validator = new ValidatorCompare<Date>(CompareOperator.GREATER_OR_EQUAL, date);
    verifyPositiveValidation(validator, null);
    verifyPositiveValidation(validator, date);
    verifyPositiveValidation(validator, new Date());

    // test i18n message
    Locale locale = AbstractNlsMessage.LOCALE_ROOT;
    String tz = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT, locale);
    ValidationFailure failure = verifyNegativeValidation(validator, isoUtil.parseDate("2000-01-31T23:59:58"),
        "The value (1/31/00 11:59:58 PM " + tz + ") needs to be greater or equal to \"1/31/00 11:59:59 PM " + tz
            + "\"!");

    // test German localization
    locale = Locale.GERMANY;
    tz = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT, locale);
    assertEquals("Der Wert (31.01.00 23:59:58 " + tz + ") muss größer oder gleich \"31.01.00 23:59:59 " + tz
        + "\" sein!", failure.getMessage(locale));
  }
}
