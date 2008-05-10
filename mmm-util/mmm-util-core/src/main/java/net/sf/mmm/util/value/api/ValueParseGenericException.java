/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.nls.base.NlsBundleUtilCore;

/**
 * This exception is thrown if parsing of a value failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueParseGenericException extends ValueParseException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3582667620565727523L;

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be
   *        converted to.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   */
  public ValueParseGenericException(Object value, Type targetType, Object valueSource) {

    super(NlsBundleUtilCore.ERR_PARSE_SOURCE, value, targetType, valueSource);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be
   *        converted to.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   */
  public ValueParseGenericException(Throwable nested, Object value, Type targetType,
      Object valueSource) {

    super(nested, NlsBundleUtilCore.ERR_PARSE_SOURCE, value, targetType, valueSource);
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be
   *        converted to.
   */
  public ValueParseGenericException(Object value, Type targetType) {

    super(NlsBundleUtilCore.ERR_PARSE, value, targetType);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be
   *        converted to.
   */
  public ValueParseGenericException(Throwable nested, Object value, Type targetType) {

    super(nested, NlsBundleUtilCore.ERR_PARSE, value, targetType);
  }

}
