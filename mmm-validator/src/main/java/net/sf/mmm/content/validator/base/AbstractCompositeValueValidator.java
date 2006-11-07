/* $Id$ */
package net.sf.mmm.content.validator.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.content.validator.api.ValidationResult;
import net.sf.mmm.content.validator.api.ValueValidator;
import net.sf.mmm.util.xml.XmlException;
import net.sf.mmm.util.xml.api.XmlWriter;

/**
 * This is an abstract base implementation of a composite ValueValidatorIF.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractCompositeValueValidator implements ValueValidator {

  /** the list of child validators */
  List<ValueValidator> children;

  /**
   * The constructor.
   */
  public AbstractCompositeValueValidator() {

    super();
    this.children = new ArrayList<ValueValidator>();
  }

  /**
   * This method gets the number of child validators.
   * 
   * @return the number of child validators.
   */
  public int getChildCount() {

    return this.children.size();
  }

  /**
   * This method gets the child validator at the given position.
   * 
   * @param index
   *        is the position of the requested child validator.
   * @return the child validator at the given index.
   */
  public ValueValidator getChild(int index) {

    return this.children.get(index);
  }

  /**
   * This method adds a child validator to this composite validator.
   * 
   * @param child
   *        is the new child validator to add.
   */
  public void add(ValueValidator child) {

    this.children.add(child);
  }

  /**
   * This method gets the validation result if this composite validator has no
   * children.
   * 
   * @return the result if empty.
   */
  protected ValidationResult getResultIfEmpty() {

    return ValidationResultImpl.VALID_RESULT;
  }

  /**
   * This method is called after the child validators are evaluated and creates
   * the validation result of the collected information.
   * 
   * @param details
   *        is the array containing the invalid results of the child validtors.
   * @param detailCount
   *        is the number of invalid results in the array (may be less than the
   *        array length).
   * @param succeedCount
   *        is the number of child validators that succeeded.
   * @return the final validation result.
   */
  protected abstract ValidationResult getResult(ValidationResult[] details, int detailCount,
      int succeedCount);

  /**
   * This method gets the maximum number of child validators that must succeed
   * until no further child validators are evaluated. <br>
   * E.g. if you want to create a composite validator with OR logic you can
   * override this method and return <code>1</code>. Then the
   * {@link AbstractCompositeValueValidator#getResult(ValidationResult[], int, int)}
   * method will be called after the first validator suceeded. <br>
   * The default value is <code>Integer.MAX_VALUE</code> so all child
   * validators are always performed.
   * 
   * @return the maximum number of child validators that need to succeed until
   *         the validation result can be generated.
   */
  protected int getMaximumRequiredValidChildren() {

    // return getChildCount();
    return Integer.MAX_VALUE;
  }

  /**
   * @see net.sf.mmm.content.validator.api.ValueValidator#validate(Object)
   */
  public ValidationResult validate(Object value) {

    int length = getChildCount();
    if (length == 0) {
      return getResultIfEmpty();
    }
    ValidationResult[] details = new ValidationResult[length];
    int invalidCount = 0;
    int succeedCount = 0;
    for (int i = 0; i < length; i++) {
      ValidationResult result = getChild(i).validate(value);
      if (result.isValid()) {
        succeedCount++;
      } else {
        details[invalidCount] = result;
        invalidCount++;
      }
      if (succeedCount >= getMaximumRequiredValidChildren()) {
        break;
      }
    }
    return getResult(details, invalidCount, succeedCount);
  }

  /**
   * @see net.sf.mmm.util.xml.api.XmlSerializable#toXml(XmlWriter)
   */
  public void toXml(XmlWriter xmlWriter) throws XmlException {

    xmlWriter.writeStartElement(XML_TAG_VALIDATOR);
    xmlWriter.writeAttribute(XML_ATR_VALIDATOR_TYPE, getClass().toString());
    for (int i = 0; i < getChildCount(); i++) {
      getChild(i).toXml(xmlWriter);
    }
    xmlWriter.writeEndElement(XML_TAG_VALIDATOR);
  }

}
