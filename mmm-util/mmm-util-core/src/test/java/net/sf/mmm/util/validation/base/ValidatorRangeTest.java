/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Locale;

import net.sf.mmm.util.validation.api.ValidationFailure;

import org.junit.Test;

/**
 * This is the test-case for {@link ValidatorRange}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidatorRangeTest extends AbstractValidatorTest {

  /**
   * Tests {@link ValidatorRange} for various comparisons of {@link Integer}s.
   */
  @Test
  public void testIntegers() {

    int min = 0;
    int max = 42;
    ValidatorRange<Integer> validator = new ValidatorRange<Integer>(Integer.valueOf(min), Integer.valueOf(max));
    verifyPositiveValidation(validator, null);
    verifyPositiveValidation(validator, Integer.valueOf(min));
    verifyPositiveValidation(validator, Integer.valueOf(max));
    verifyPositiveValidation(validator, Integer.valueOf(min + 1));
    verifyPositiveValidation(validator, Integer.valueOf(max - 1));
    int valueLow = min - 1;
    ValidationFailure failure = verifyNegativeValidation(validator, Integer.valueOf(valueLow), "The value " + valueLow
        + " needs to be in the range from " + min + " to " + max + ".");
    assertEquals("Der Wert " + valueLow + " muss im Bereich von " + min + " bis " + max + " liegen.",
        failure.getMessage(Locale.GERMANY));
  }

}
