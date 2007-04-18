/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import net.sf.mmm.value.NlsBundleValueCore;
import net.sf.mmm.value.api.GenericValue;

/**
 * This exception is thrown if a value has the wrong type (a different value
 * type was expected).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class WrongValueTypeException extends ValueException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3681394831124284211L;

  /**
   * The constructor.
   * 
   * @param genericValue
   *        is the generic value that has the wrong type.
   * @param expectedType
   *        is the expected type of the value.
   */
  public WrongValueTypeException(GenericValue genericValue, Class<?> expectedType) {

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
  public WrongValueTypeException(GenericValue genericValue, Class<?> expectedType, Throwable nested) {

    super(nested, NlsBundleValueCore.ERR_VALUE_WRONG_TYPE, genericValue, getType(genericValue),
        expectedType);
  }

  /**
   * This method gets the type reflecting the actual value contained in the
   * <code>genericValue</code>.
   * 
   * @param genericValue
   *        is the value for which the type is requested.
   * @return the type of the given <code>genericValue</code>.
   */
  private static Class<?> getType(GenericValue genericValue) {

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
  public GenericValue getGenericValue() {

    return (GenericValue) getNlsMessage().getArgument(0);
  }

  /**
   * This method gets the expected value type.
   * 
   * @return the type that was expected.
   */
  public Class<?> getExpectedType() {

    return (Class<?>) getNlsMessage().getArgument(2);
  }

}
