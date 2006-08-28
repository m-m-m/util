/* $Id$ */
package net.sf.mmm.content.validator.impl;

import net.sf.mmm.content.validator.api.ValidationResultIF;
import net.sf.mmm.content.validator.base.AbstractCompositeValueValidator;
import net.sf.mmm.content.validator.base.ValidationResult;
import net.sf.mmm.nls.base.NlsMessage;

/**
 * This is the implementation of a composite validator that declares a value as
 * valid if and only if at least one child validator does.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueOrValidator extends AbstractCompositeValueValidator {

    /** uid for serialization */
    private static final long serialVersionUID = -9206244114248240571L;

    /**
     * The constructor.
     */
    public ValueOrValidator() {

        super();
    }

    /**
     * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getResult(net.sf.mmm.content.validator.api.ValidationResultIF[],
     *      int, int)
     */
    protected ValidationResultIF getResult(ValidationResultIF[] details, int detailCount,
            int succeedCount) {

        if (succeedCount >= 1) {
            return ValidationResult.VALID_RESULT;
        } else {
            // TODO: i18n
            return new ValidationResult(new NlsMessage(
                    "At least one of the \"{0}\" error(s) must be fixed:", detailCount), details,
                    detailCount);
        }
    }

    /**
     * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getMaximumRequiredValidChildren()
     */
    protected int getMaximumRequiredValidChildren() {

        return 1;
    }

    /**
     * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getResultIfEmpty()
     */
    protected ValidationResultIF getResultIfEmpty() {

        // TODO: i18n
        return new ValidationResult("OR-Validator needs at least one child validator to succeed!");
    }
}