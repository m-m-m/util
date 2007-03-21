/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.validator.impl;

import net.sf.mmm.content.validator.api.ValidationResult;
import net.sf.mmm.content.validator.base.AbstractValueValidator;
import net.sf.mmm.content.validator.base.ValidationResultImpl;

/**
 * This is an implementation of the ValueValidatorIF interface that that the
 * value is not <code>null</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueNotNullValidator extends AbstractValueValidator {

    /** uid for serialization */
    private static final long serialVersionUID = -2115860083937529737L;

    /**
     * The constructor.
     */
    public ValueNotNullValidator() {

        super();
    }

    /**
     * {@inheritDoc}
     */
    public ValidationResult validate(Object value) {

        if (value == null) {
            //TODO: i18n
            return new ValidationResultImpl("The value is null!");
            //"The value must have the type {0}!",
            //new Object[]{this.type});
        } else {
            return ValidationResultImpl.VALID_RESULT;            
        }
    }
}