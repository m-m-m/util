/* $Id$ */
package net.sf.mmm.content.validator.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is thrown if a validation failes. It describes why the value
 * is invalid.<br>
 * TODO: add child details to stack trace.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidationException extends NlsException {

    /** uid for serialization */
    private static final long serialVersionUID = 6180310118551885467L;

    /** the result of the validation */
    private final ValidationResult result;

    /**
     * The construtor.
     * 
     * @see NlsException#NlsException(net.sf.mmm.nls.api.NlsMessageIF)
     * 
     * @param validationResult
     *        is the result of the failed validation. The result must NOT be
     *        {@link ValidationResult#isValid() valid}.
     */
    public ValidationException(ValidationResult validationResult) {

        super(validationResult.getMessage());
        this.result = validationResult;
    }

    /**
     * The constructor.
     * 
     * @see NlsException#NlsException(Throwable,
     *      net.sf.mmm.nls.api.NlsMessageIF)
     * 
     * @param validationResult
     *        is the result of the failed validation. The result must NOT be
     *        {@link ValidationResult#isValid() valid}.
     * @param nested
     *        is the throwable that caused this error.
     */
    public ValidationException(ValidationResult validationResult, Throwable nested) {

        super(nested, validationResult.getMessage());
        this.result = validationResult;
    }

    /**
     * This method gets the detailed result of the validation that failed.
     * 
     * @return the result of the validation.
     */
    public ValidationResult getResult() {

        return this.result;
    }

}
