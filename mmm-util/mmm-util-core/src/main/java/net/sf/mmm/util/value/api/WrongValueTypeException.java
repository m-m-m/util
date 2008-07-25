/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.NlsBundleUtilCore;

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
   * @param value is the wrong value.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Object value, Class<?> expectedType) {

    super(NlsBundleUtilCore.ERR_VALUE_WRONG_TYPE, value, getType(value), expectedType);
  }

  /**
   * The constructor.
   * 
   * @param nested is the throwable that caused this exception.
   * @param value is the wrong value.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Throwable nested, Object value, Class<?> expectedType) {

    super(nested, NlsBundleUtilCore.ERR_VALUE_WRONG_TYPE, value, getType(value), expectedType);
  }

  /**
   * The constructor.
   * 
   * @param value is the wrong value.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Object value, Object valueSource, Class<?> expectedType) {

    super(NlsBundleUtilCore.ERR_VALUE_WRONG_TYPE_SOURCE, value, getType(value), expectedType,
        valueSource);
  }

  /**
   * The constructor.
   * 
   * @param nested is the throwable that caused this exception.
   * @param value is the wrong value.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param expectedType is the expected type of the value.
   */
  public WrongValueTypeException(Throwable nested, Object value, Object valueSource,
      Class<?> expectedType) {

    super(nested, NlsBundleUtilCore.ERR_VALUE_WRONG_TYPE_SOURCE, value, getType(value),
        expectedType, valueSource);
  }

  /**
   * This method gets the type reflecting the actual value.
   * 
   * @param value is the value for which the type is requested.
   * @return the type of the given <code>value</code>.
   */
  private static Class<?> getType(Object value) {

    if (value == null) {
      return null;
    } else {
      return value.getClass();
    }
  }

}
