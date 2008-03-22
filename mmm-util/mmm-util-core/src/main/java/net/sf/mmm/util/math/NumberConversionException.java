/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math;

import net.sf.mmm.util.nls.NlsRuntimeException;
import net.sf.mmm.util.nls.base.NlsBundleUtilCore;

/**
 * This is the exception thrown if a numeric value can NOT converted to a
 * specific number-type.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NumberConversionException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7112183497163125178L;

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be converted.
   * @param targetType represents the type the <code>value</code> should have
   *        been converted to. This will typically be a {@link Class} object.
   */
  public NumberConversionException(Object value, Object targetType) {

    super(NlsBundleUtilCore.ERR_NUMBER_CONVERSION, value, targetType);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be converted.
   * @param targetType represents the type the <code>value</code> should have
   *        been converted to. This will typically be a {@link Class} object.
   */
  public NumberConversionException(Throwable nested, Object value, Object targetType) {

    super(nested, NlsBundleUtilCore.ERR_NUMBER_CONVERSION, value, targetType);
  }

}
