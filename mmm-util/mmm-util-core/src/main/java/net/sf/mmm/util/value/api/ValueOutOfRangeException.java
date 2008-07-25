/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * This is the exception thrown if a numeric value is not in the expected range.
 * 
 * @see GenericValueConverter#convertValue(Object, Object, Number, Number)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueOutOfRangeException extends ValueException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3363522277063736719L;

  /**
   * The constructor.
   * 
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   */
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum) {

    super(NlsBundleUtilCore.ERR_VALUE_OUT_OF_RANGE, value, minimum, maximum);
    assert ((value.doubleValue() > minimum.doubleValue()) || (value.doubleValue() < minimum
        .doubleValue()));
  }

  /**
   * The constructor.
   * 
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   */
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum, Object valueSource) {

    super(NlsBundleUtilCore.ERR_VALUE_OUT_OF_RANGE_SOURCE, value, minimum, maximum, valueSource);
    assert ((value.doubleValue() > minimum.doubleValue()) || (value.doubleValue() < minimum
        .doubleValue()));
  }

}
