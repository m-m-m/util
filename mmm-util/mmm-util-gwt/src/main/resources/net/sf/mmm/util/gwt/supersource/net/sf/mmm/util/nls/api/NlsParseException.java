/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * The {@link NlsParseException} is thrown if some data could NOT be parsed because it does NOT match the
 * according format (pattern or grammar).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
@SuppressWarnings("rawtypes")
public class NlsParseException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5909310169818471597L;

  /** Key for the {@link NlsMessage#getArgument(String) argument} {@value}. */
  public static final String KEY_FORMAT = "format";

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param expected is the string or character that was expected but not found at the end of
   *        <code>value</code>.
   */
  public NlsParseException(CharSequence value, CharSequence expected) {

    super(NlsBundleUtilCore.ERR_PARSE_EXPECTED, toMap(KEY_VALUE, value, KEY_TYPE, expected));
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param requiredFormat describes the expected format (e.g. "[+-][0-9]*[.][0-9]+")
   * @param type is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(CharSequence value, CharSequence requiredFormat, Object type) {

    super(NlsBundleUtilCore.ERR_PARSE_FORMAT, toMap(KEY_VALUE, value, KEY_TYPE, type, KEY_FORMAT, requiredFormat));
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param requiredFormat describes the expected format (e.g. "[+-][0-9]*[.][0-9]+")
   * @param type is type the given <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  public NlsParseException(CharSequence value, CharSequence requiredFormat, Object type, Object valueSource) {

    super(NlsBundleUtilCore.ERR_PARSE_FORMAT, addToMap(
        toMap(KEY_VALUE, value, KEY_TYPE, type, KEY_FORMAT, requiredFormat), KEY_SOURCE, valueSource));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param requiredFormat describes the expected format (e.g. "[+-][0-9]*[.][0-9]+")
   * @param type is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(Throwable nested, CharSequence value, CharSequence requiredFormat, Object type) {

    super(nested, NlsBundleUtilCore.ERR_PARSE_FORMAT, toMap(KEY_VALUE, value, KEY_TYPE, type, KEY_FORMAT,
        requiredFormat));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param requiredFormat describes the expected format (e.g. "[+-][0-9]*[.][0-9]+")
   * @param type is type the given <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  public NlsParseException(Throwable nested, CharSequence value, CharSequence requiredFormat, Object type,
      Object valueSource) {

    super(nested, NlsBundleUtilCore.ERR_PARSE_FORMAT, addToMap(
        toMap(KEY_VALUE, value, KEY_TYPE, type, KEY_FORMAT, requiredFormat), KEY_SOURCE, valueSource));
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  public NlsParseException(Object value, Class targetType, Object valueSource) {

    super(NlsBundleUtilCore.ERR_PARSE_TYPE_SOURCE, toMap(KEY_VALUE, value, KEY_TYPE, targetType, KEY_SOURCE,
        valueSource));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be converted to.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  public NlsParseException(Throwable nested, Object value, Class targetType, Object valueSource) {

    super(nested, NlsBundleUtilCore.ERR_PARSE_TYPE_SOURCE, toMap(KEY_VALUE, value, KEY_TYPE, targetType, KEY_SOURCE,
        valueSource));
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(Object value, Class targetType) {

    super(NlsBundleUtilCore.ERR_PARSE_TYPE, toMap(KEY_VALUE, value, KEY_TYPE, targetType));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(Throwable nested, Object value, Class targetType) {

    super(nested, NlsBundleUtilCore.ERR_PARSE_TYPE, toMap(KEY_VALUE, value, KEY_TYPE, targetType));
  }

}
