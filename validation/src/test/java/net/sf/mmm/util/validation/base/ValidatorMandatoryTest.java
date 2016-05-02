/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Locale;

import net.sf.mmm.util.validation.api.ValidationFailure;

import org.junit.Test;

/**
 * This is the test-case for {@link ValidatorMandatory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidatorMandatoryTest extends AbstractValidatorTest {

  /** I18n failure message. */
  private static final String FAILURE_MESSAGE = "The value has to be filled.";

  /**
   * Tests {@link ValidatorMandatory} fails for {@code null}.
   */
  @Test
  public void testNull() {

    ValidationFailure failure = verifyNegativeValidation(ValidatorMandatory.getInstance(), null, FAILURE_MESSAGE);
    assertEquals("Wert muss gefüllt werden.", failure.getMessage(Locale.GERMANY));
  }

  /**
   * Tests {@link ValidatorMandatory} fails for {@code ""}.
   */
  @Test
  public void testEmptyString() {

    verifyNegativeValidation(ValidatorMandatory.getInstance(), "", FAILURE_MESSAGE);
  }

  /**
   * Tests {@link ValidatorMandatory} fails for {@link Collections#emptyList()}.
   */
  @Test
  public void testEmptyList() {

    verifyNegativeValidation(ValidatorMandatory.getInstance(), Collections.EMPTY_LIST, FAILURE_MESSAGE);
  }

  /**
   * Tests {@link ValidatorMandatory} succeeds for {@code "some value"}.
   */
  @Test
  public void testFilledString() {

    verifyPositiveValidation(ValidatorMandatory.getInstance(), "some value");
  }

  /**
   * Tests {@link ValidatorMandatory} succeeds for {@link BigDecimal#ZERO}.
   */
  @Test
  public void testFilledZero() {

    verifyPositiveValidation(ValidatorMandatory.getInstance(), BigDecimal.ZERO);
  }

}
