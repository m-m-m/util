/* $Id$ */
package net.sf.mmm.content.validator.base;

import net.sf.mmm.content.validator.api.ValidationResultIF;
import net.sf.mmm.nls.api.NlsMessageIF;
import net.sf.mmm.nls.base.NlsMessage;

/**
 * This is the default implementation of the ValidationResultIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidationResult implements ValidationResultIF {

    /** this constant holds the "singleton" result of a successful validation */
    public static final ValidationResultIF VALID_RESULT = new ValidationResult();

    /** the constant used if no details are available */
    private static final ValidationResultIF[] NO_DETAILS = new ValidationResultIF[0];

    /** the child results */
    private ValidationResultIF[] details;

    /** the error message */
    private NlsMessageIF message;

    /** the number of details available (may be less than array length) */
    private int detailCount;

    /**
     * The constructor for a valid result.
     * 
     * This constructor is private - use the
     * {@link ValidationResult#VALID_RESULT}constant to get a valid result.
     */
    private ValidationResult() {

        super();
        this.message = null;
        this.details = NO_DETAILS;
        this.detailCount = 0;
    }
    
    /**
     * The constructor for an atomic invalid result.
     * 
     * @param invalidationMessage
     *        is the message describing why the object is invalid.
     */
    public ValidationResult(String invalidationMessage) {

        this(new NlsMessage(invalidationMessage));
    }

    /**
     * The constructor for an atomic invalid result.
     * 
     * @param invalidationMessage
     *        is the message describing why the object is invalid.
     */
    public ValidationResult(NlsMessageIF invalidationMessage) {

        this(invalidationMessage, NO_DETAILS);
    }

    /**
     * The constructor for a composite invalid result.
     * 
     * @param invalidationMessage
     *        is the message describing why the object is invalid.
     * @param detailedChildResults
     *        is the array containing the results of all child validations that
     *        failed.
     */
    public ValidationResult(NlsMessageIF invalidationMessage,
            ValidationResultIF ... detailedChildResults) {

        this(invalidationMessage, detailedChildResults,
                detailedChildResults.length);
    }

    /**
     * The constructor for a composite invalid result.
     * 
     * @param invalidationMessage
     *        is the message describing why the object is invalid.
     * @param detailedChildResults
     *        is the array containing the results of all child validations that
     *        failed.
     * @param detailedChildCount
     *        is the number of child results in the
     *        <code>detailedChildResults</code> array. The value must NOT be
     *        greater than the length of the array.
     */
    public ValidationResult(NlsMessageIF invalidationMessage,
            ValidationResultIF[] detailedChildResults, int detailedChildCount) {

        super();
        //assert (detailedChildCount > detailedChildResults.length);
        this.message = invalidationMessage;
        this.details = detailedChildResults;
        this.detailCount = detailedChildCount;
    }

    /**
     * @see net.sf.mmm.content.validator.api.ValidationResultIF#isValid()
     */
    public boolean isValid() {

        return (getMessage() == null);
    }

    /**
     * @see net.sf.mmm.content.validator.api.ValidationResultIF#getMessage()
     */
    public NlsMessageIF getMessage() {

        return this.message;
    }

    /**
     * @see net.sf.mmm.content.validator.api.ValidationResultIF#getDetailCount()
     */
    public int getDetailCount() {

        return this.detailCount;
    }

    /**
     * @see net.sf.mmm.content.validator.api.ValidationResultIF#getDetail(int)
     */
    public ValidationResultIF getDetail(int index) {

        return this.details[index];
    }

}