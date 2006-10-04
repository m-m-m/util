/* $ Id: $ */
package net.sf.mmm.value.api;

import net.sf.mmm.value.CoreNlsResourceBundle;
import net.sf.mmm.value.api.GenericValueIF;

/**
 * This expeption is thrown if a value has the wrong type (a different value
 * type was expected).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class WrongValueTypeException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 3681394831124284211L;

  /**
   * The constructor.
   * 
   * @param genericValue
   *        is the generic value that has the wrong type.
   * @param expectedType
   *        is the expected type of the value.
   */
  public WrongValueTypeException(GenericValueIF genericValue, Class expectedType) {

    this(genericValue, expectedType, null);
  }

  /**
   * The constructor.
   * 
   * @param genericValue
   *        is the generic value that has the wrong type.
   * @param expectedType
   *        is the expected type of the value.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public WrongValueTypeException(GenericValueIF genericValue, Class expectedType, Throwable nested) {

    super(nested, CoreNlsResourceBundle.ERR_VALUE_WRONG_TYPE, genericValue, getType(genericValue),
        expectedType);
  }

  private static Class getType(GenericValueIF genericValue) {

    Object value = genericValue.getObject(null);
    if (value == null) {
      return null;
    } else {
      return value.getClass();
    }
  }

  /**
   * This method gets the value that has the wrong type.
   * 
   * @return the wrong typed value.
   */
  public GenericValueIF getGenericValue() {

    return (GenericValueIF) getNlsMessage().getArgument(0);
  }

  /**
   * This method gets the expected value type.
   * 
   * @return the type that was expected.
   */
  public Class getExpectedType() {

    return (Class) getNlsMessage().getArgument(2);
  }

}
