/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;

/**
 * The {@link NlsParseException} is thrown if some data could NOT be parsed because it does NOT match the
 * according format (pattern or grammar).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.1
 */
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

    this((Throwable) null, value, expected);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param expected is the string or character that was expected but not found at the end of
   *        <code>value</code>.
   * @since 2.0.2
   */
  public NlsParseException(Throwable nested, CharSequence value, CharSequence expected) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorParseExpected(value, expected));
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param requiredFormat describes the expected format (e.g. "[+-][0-9]*[.][0-9]+")
   * @param type is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(CharSequence value, CharSequence requiredFormat, Object type) {

    this((Throwable) null, value, requiredFormat, type);
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

    this((Throwable) null, value, requiredFormat, type, valueSource);
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

    this(nested, value, requiredFormat, type, null);
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

    super(createBundle(NlsMessagesBundleUtilCore.class).errorParseFormat(value, requiredFormat, type, valueSource));
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
  public NlsParseException(Object value, Type targetType, Object valueSource) {

    this((Throwable) null, value, targetType, valueSource);
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
  public NlsParseException(Throwable nested, Object value, Type targetType, Object valueSource) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorParseType(value, targetType, valueSource));
  }

  /**
   * The constructor.
   * 
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(Object value, Type targetType) {

    this(value, targetType, null);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param value is the value that could NOT be parsed.
   * @param targetType is type the given <code>value</code> should be converted to.
   */
  public NlsParseException(Throwable nested, Object value, Type targetType) {

    this(nested, value, targetType, null);
  }

}
