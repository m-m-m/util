/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the exception thrown if a date given as string was illegal.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class IllegalDateFormatException extends NlsIllegalArgumentException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5008989969842672695L;

  /**
   * The constructor.
   * 
   * @param dateString is the string that was supposed to be a valid date but is
   *        NOT.
   */
  public IllegalDateFormatException(String dateString) {

    super(NlsBundleUtilCore.ERR_ILLEGAL_DATA_FORMAT, dateString);
  }

  /**
   * The constructor.
   * 
   * @param dateString is the string that was supposed to be a valid date but is
   *        NOT.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public IllegalDateFormatException(String dateString, Exception nested) {

    super(nested, NlsBundleUtilCore.ERR_ILLEGAL_DATA_FORMAT, dateString);
  }

}
