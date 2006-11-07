/* $Id$ */
package net.sf.mmm.content.validator.base;

import net.sf.mmm.content.validator.api.ValidationResult;
import net.sf.mmm.nls.api.NlsMessage;
import net.sf.mmm.nls.base.NlsMessageImpl;

/**
 * This is the default implementation of the ValidationResultIF interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValidationResultImpl implements ValidationResult {

  /** this constant holds the "singleton" result of a successful validation */
  public static final ValidationResult VALID_RESULT = new ValidationResultImpl();

  /** the constant used if no details are available */
  private static final ValidationResult[] NO_DETAILS = new ValidationResult[0];

  /** the child results */
  private ValidationResult[] details;

  /** the error message */
  private NlsMessage message;

  /** the number of details available (may be less than array length) */
  private int detailCount;

  /**
   * The constructor for a valid result.
   * 
   * This constructor is private - use the
   * {@link ValidationResultImpl#VALID_RESULT}constant to get a valid result.
   */
  private ValidationResultImpl() {

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
  public ValidationResultImpl(String invalidationMessage) {

    this(new NlsMessageImpl(invalidationMessage));
  }

  /**
   * The constructor for an atomic invalid result.
   * 
   * @param invalidationMessage
   *        is the message describing why the object is invalid.
   */
  public ValidationResultImpl(NlsMessage invalidationMessage) {

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
  public ValidationResultImpl(NlsMessage invalidationMessage,
      ValidationResult... detailedChildResults) {

    this(invalidationMessage, detailedChildResults, detailedChildResults.length);
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
  public ValidationResultImpl(NlsMessage invalidationMessage,
      ValidationResult[] detailedChildResults, int detailedChildCount) {

    super();
    // assert (detailedChildCount > detailedChildResults.length);
    this.message = invalidationMessage;
    this.details = detailedChildResults;
    this.detailCount = detailedChildCount;
  }

  /**
   * @see net.sf.mmm.content.validator.api.ValidationResult#isValid()
   */
  public boolean isValid() {

    return (getMessage() == null);
  }

  /**
   * @see net.sf.mmm.content.validator.api.ValidationResult#getMessage()
   */
  public NlsMessage getMessage() {

    return this.message;
  }

  /**
   * @see net.sf.mmm.content.validator.api.ValidationResult#getDetailCount()
   */
  public int getDetailCount() {

    return this.detailCount;
  }

  /**
   * @see net.sf.mmm.content.validator.api.ValidationResult#getDetail(int)
   */
  public ValidationResult getDetail(int index) {

    return this.details[index];
  }

}
