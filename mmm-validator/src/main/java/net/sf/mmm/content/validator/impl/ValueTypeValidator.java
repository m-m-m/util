/* $Id$ */
package net.sf.mmm.content.validator.impl;

import net.sf.mmm.content.validator.api.ValidationResultIF;
import net.sf.mmm.content.validator.base.AbstractValueValidator;
import net.sf.mmm.content.validator.base.ValidationResult;

/**
 * This is an implementation of the ValueValidatorIF interface that checks if a
 * value has a specific type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueTypeValidator extends AbstractValueValidator {

    /** uid for serialization */
    private static final long serialVersionUID = 8221282496616883367L;
    
    /** the required value type */
    private Class type;

    /**
     * The constructor.
     * 
     * @param valueType
     *        is the required value type.
     */
    public ValueTypeValidator(Class valueType) {

        super();
        this.type = valueType;
    }

    /**
     * @see net.sf.mmm.content.validator.api.ValueValidatorIF#validate(Object)
     */
    public ValidationResultIF validate(Object value) {

        if ((value == null) || (value.getClass().equals(this.type))) {
            return ValidationResult.VALID_RESULT;            
        } else {
            //TODO: i18n
            return new ValidationResult("The value must have the type "
                    + this.type + "!");
            //"The value must have the type {0}!",
            //new Object[]{this.type});
        }
    }
}