/* $Id$ */
package net.sf.mmm.content.validator.impl;

import net.sf.mmm.content.validator.api.ValidationResult;
import net.sf.mmm.content.validator.base.AbstractCompositeValueValidator;
import net.sf.mmm.content.validator.base.ValidationResultImpl;
import net.sf.mmm.nls.base.NlsMessageImpl;

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
     * @see net.sf.mmm.content.validator.base.AbstractCompositeValueValidator#getResult(net.sf.mmm.content.validator.api.ValidationResult[],
     *      int, int)
     */
    protected ValidationResult getResult(ValidationResult[] details, int detailCount,
            int succeedCount) {

        if (succeedCount == getChildCount()) {
            return ValidationResultImpl.VALID_RESULT;
        } else {
            // TODO: i18n
            return new ValidationResultImpl(new NlsMessageImpl("\"{0}\" error(s):", detailCount), details,
                    detailCount);
        }
    }
}