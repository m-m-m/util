/* $ Id: $ */
package net.sf.mmm.value.api;

import net.sf.mmm.value.CoreNlsResourceBundle;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.value.api.MutableGenericValueIF value} was edited without
 * being
 * {@link net.sf.mmm.value.api.MutableGenericValueIF#isEditable() editable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueNotEditableException extends ValueException {

    /** uid for serialization */
    private static final long serialVersionUID = 8537295080260761963L;

    /**
     * The constructor.
     * 
     * @param genericValue
     *        is the node that is NOT
     *        {@link net.sf.mmm.value.api.MutableGenericValueIF#isEditable() editable} .
     */
    public ValueNotEditableException(MutableGenericValueIF genericValue) {

        super(CoreNlsResourceBundle.ERR_NODE_NOT_EDITABLE, genericValue);
    }

    /**
     * The constructor.
     * 
     * @param genericValue
     *        is the node that is NOT
     *        {@link net.sf.mmm.value.api.MutableGenericValueIF#isEditable() editable} .
     * @param nested
     *        is the throwable that caused this exception.
     */
    public ValueNotEditableException(MutableGenericValueIF genericValue, Throwable nested) {

        super(nested, CoreNlsResourceBundle.ERR_NODE_NOT_EDITABLE, genericValue);
    }

    /**
     * This method gets the node that was edited without being
     * {@link MutableGenericValueIF#isEditable() editable}.
     * 
     * @return the associated configuration node.
     */
    public MutableGenericValueIF getGenericValue() {

        return (MutableGenericValueIF) getNlsMessage().getArgument(0);
    }

}
