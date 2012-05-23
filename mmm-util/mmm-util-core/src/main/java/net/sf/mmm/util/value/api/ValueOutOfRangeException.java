/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * This is the exception thrown if a numeric value is not in the expected range.
 * 
 * @see GenericValueConverter#convertValue(Object, Object, Number, Number)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
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

    this(value, minimum, maximum, null);
  }

  /**
   * The constructor.
   * 
   * @param value is the number that is out of range.
   * @param minimum is the minimum value allowed
   * @param maximum is the maximum value allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  public ValueOutOfRangeException(Number value, Number minimum, Number maximum, Object valueSource) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorValueOutOfRangeWithSource(value, minimum, maximum,
        valueSource));
    assert ((value.doubleValue() > minimum.doubleValue()) || (value.doubleValue() < minimum.doubleValue()));
  }

  /**
   * This method checks that the given <code>value</code> is in the inclusive range from <code>minimum</code>
   * to <code>maximum</code>.
   * 
   * @param value is the value to check.
   * @param minimum is the minimum number allowed.
   * @param maximum is the maximum number allowed.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier. It may be
   *        <code>null</code> if there is no helpful source available.
   * @throws ValueOutOfRangeException - if the given <code>value</code> is NOT in the range from
   *         <code>minimum</code> to <code>maximum</code>.
   */
  public static void checkRange(Number value, Number minimum, Number maximum, Object valueSource)
      throws ValueOutOfRangeException {

    double d = value.doubleValue();
    if ((d < minimum.doubleValue()) || (d > maximum.doubleValue())) {
      if (valueSource == null) {
        throw new ValueOutOfRangeException(value, minimum, maximum);
      } else {
        throw new ValueOutOfRangeException(value, minimum, maximum, valueSource);
      }
    }
  }

}
