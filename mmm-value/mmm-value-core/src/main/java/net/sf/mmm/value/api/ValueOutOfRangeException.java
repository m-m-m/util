/* $ Id: $ */
package net.sf.mmm.value.api;

import net.sf.mmm.value.CoreNlsResourceBundle;
import net.sf.mmm.value.api.GenericValueIF;

/**
 * This is the exception thrown if a value (e.g.
 * {@link net.sf.mmm.value.api.GenericValueIF#getInteger() integer}) is not in
 * the expected range.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueOutOfRangeException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 3363522277063736719L;

  /**
   * The constructor.
   * 
   * @param genericValue
   *        is the generic value that is out of range.
   * @param value
   *        is the number that is out of range.
   * @param minimum
   *        is the minimum value allowed
   * @param maximum
   *        is the maximum value allowed.
   */
  public ValueOutOfRangeException(GenericValueIF genericValue, Number value, Number minimum,
      Number maximum) {

    super(CoreNlsResourceBundle.ERR_VALUE_OUT_OF_RANGE, genericValue, value, minimum, maximum);
    assert ((value.doubleValue() > minimum.doubleValue()) || (value.doubleValue() < minimum
        .doubleValue()));
  }

  /**
   * This method gets the value that is out of range
   * 
   * @return the value out of range.
   */
  public GenericValueIF getGenericValue() {

    return (GenericValueIF) getNlsMessage().getArgument(0);
  }

}
