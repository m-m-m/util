/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.validator.impl;

import net.sf.mmm.util.nls.NlsAccess;
import net.sf.mmm.value.validator.api.ValidationResult;
import net.sf.mmm.value.validator.base.AbstractCompositeValueValidator;
import net.sf.mmm.value.validator.base.ValidationResultImpl;

/**
 * This is the implementation of a composite validator that declares a value as
 * valid if and only if all child validators do.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueAndValidator extends AbstractCompositeValueValidator {

  /** uid for serialization */
  private static final long serialVersionUID = 7866624963054137926L;

  /**
   * The constructor.
   */
  public ValueAndValidator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ValidationResult getResult(ValidationResult[] details, int detailCount, int succeedCount) {

    if (succeedCount == getChildCount()) {
      return ValidationResultImpl.VALID_RESULT;
    } else {
      // TODO: i18n
      return new ValidationResultImpl(NlsAccess.getFactory().create("\"{0}\" error(s):",
          Integer.valueOf(detailCount)), details, detailCount);
    }
  }
}
