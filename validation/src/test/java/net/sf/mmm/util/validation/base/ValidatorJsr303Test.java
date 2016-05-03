/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;

import net.sf.mmm.util.nls.base.AbstractNlsMessage;
import net.sf.mmm.util.validation.api.ValidationFailure;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.junit.Assert;
import org.junit.Test;

/**
 * This is the test-case for {@link ValidatorJsr303}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorJsr303Test extends Assert {

  /**
   * Test {@link ValidatorJsr303#validate(Object)} for mandatory field.
   */
  @Test
  public void testValidatorJsr303() {

    Validator validator = getValidator();
    ValidatorJsr303<String> mandatoryStringValidator = new ValidatorJsr303<>(validator, TestBean.class,
        "mandatoryString");
    ValidationFailure failure = mandatoryStringValidator.validate(null);
    assertNotNull(failure);
    assertEquals("has to be filled", failure.getMessage());
  }

  /**
   * @return the {@link Validator} to use for testing.
   */
  protected Validator getValidator() {

    MessageInterpolator interpolator = new ResourceBundleMessageInterpolator() {

      @Override
      public String interpolate(String messageTemplate, Context context) {

        return interpolate(messageTemplate, context, AbstractNlsMessage.LOCALE_ROOT);
      }

    };
    return Validation.byDefaultProvider().configure().messageInterpolator(interpolator).buildValidatorFactory()
        .getValidator();
  }

  @SuppressWarnings("javadoc")
  public class TestBean {

    @Mandatory
    private String mandatoryString;

    /**
     * @return the mandatoryString
     */
    public String getMandatoryString() {

      return this.mandatoryString;
    }

    /**
     * @param mandatoryString is the mandatoryString to set
     */
    public void setMandatoryString(String mandatoryString) {

      this.mandatoryString = mandatoryString;
    }
  }
}
