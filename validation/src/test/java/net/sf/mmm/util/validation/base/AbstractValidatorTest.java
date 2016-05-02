/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.mmm.util.lang.api.Message;
import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValueValidator;

import org.junit.Assert;

/**
 * This is the test-case for {@link ValueValidator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValidatorTest extends Assert {

  /**
   * This method verifies a negative {@link ValueValidator#validate(Object, Object) validation}.
   * 
   * @param <V> is the generic type of the {@code value}.
   * 
   * @param validator is the {@link ValueValidator} to test.
   * @param value is the value expected to be invalid.
   * @param i18nMessage is the expected {@link ValidationFailure#getMessage(java.util.Locale) message for the
   *        root locale}.
   * @return the {@link ValidationFailure} for additional asserts.
   */
  protected <V> ValidationFailure verifyNegativeValidation(ValueValidator<V> validator, V value, String i18nMessage) {

    assertNotNull(validator);
    String source = "myMagicSource";
    ValidationFailure failure = validator.validate(value, source);
    assertNotNull(failure);
    assertSame(source, failure.getSource());
    assertSame(Message.TYPE_VALIDATION_FAILURE, failure.getType());
    assertNotNull(failure.getCode());
    assertEquals(i18nMessage, failure.getMessage(AbstractNlsMessage.LOCALE_ROOT));
    return failure;
  }

  /**
   * This method verifies a positive {@link ValueValidator#validate(Object, Object) validation}.
   * 
   * @param <V> is the generic type of the {@code value}.
   * 
   * @param validator is the {@link ValueValidator} to test.
   * @param value is the value expected to be invalid.
   */
  protected <V> void verifyPositiveValidation(ValueValidator<V> validator, V value) {

    assertNotNull(validator);
    ValidationFailure failure = validator.validate(value);
    if (failure != null) {
      fail(failure.getMessage());
    }
  }

  /**
   * @param value is the object to format.
   * @param locale is the {@link Locale}.
   * @return the formatted value as {@link String}.
   */
  protected String toString(Object value, Locale locale) {

    if (value instanceof Date) {
      DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale);
      return format.format(value);
    } else {
      return value.toString();
    }
  }

}
