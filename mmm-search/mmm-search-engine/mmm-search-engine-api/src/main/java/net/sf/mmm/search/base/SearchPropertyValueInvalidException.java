/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsBundleSearchApi;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.search.api.SearchEntry#getProperty(String) property value}
 * is invalid.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchPropertyValueInvalidException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = -8530282554868568736L;

  /**
   * The constructor.
   * 
   * @param propertyName is the name of the related property.
   * @param value is the invalid value.
   */
  public SearchPropertyValueInvalidException(String propertyName, String value) {

    super(NlsBundleSearchApi.ERR_PROPERTY_VALUE_INVALID, toMap(KEY_VALUE, value, KEY_PROPERTY,
        propertyName));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param propertyName is the name of the related property.
   * @param value is the invalid value.
   */
  public SearchPropertyValueInvalidException(Throwable nested, String propertyName, String value) {

    super(nested, NlsBundleSearchApi.ERR_PROPERTY_VALUE_INVALID, toMap(KEY_VALUE, value,
        KEY_PROPERTY, propertyName));
  }

}
