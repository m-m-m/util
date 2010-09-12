/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.value.api.PojoValidator;

/**
 * This is an implementation of {@link PojoValidator} using JSR303 (Bean
 * Validation - see {@link javax.validation.Validator}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class PojoValidatorJsr303 extends AbstractValueValidator<Object> implements PojoValidator {

  /** @see #getValidatorFactory() */
  private ValidatorFactory validatorFactory;

  /**
   * The constructor.
   */
  public PojoValidatorJsr303() {

    super();
  }

  /**
   * @return the validatorFactory
   */
  protected ValidatorFactory getValidatorFactory() {

    return this.validatorFactory;
  }

  /**
   * @param validatorFactory is the validatorFactory to set
   */
  @Inject
  public void setValidatorFactory(ValidatorFactory validatorFactory) {

    this.validatorFactory = validatorFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.validatorFactory == null) {
      // Bundle with hibernate validation?
      throw new ResourceMissingException(ValidatorFactory.class.getName());
    }
  }

  /**
   * {@inheritDoc}
   */
  public void validate(Object value, Object valueSource) throws RuntimeException {

    Validator validator = this.validatorFactory.getValidator();
    // validator.getConstraintsForClass(Object.class).getConstraintsForProperty("foo").getConstraintDescriptors().iterator().next().
    Set<ConstraintViolation<Object>> violationSet = validator.validate(value);
    if (violationSet.isEmpty()) {
      // object is valid...
      return;
    }

    for (ConstraintViolation<Object> violation : violationSet) {
      // TODO ...
      String message = violation.getMessageTemplate();
    }
  }
}
