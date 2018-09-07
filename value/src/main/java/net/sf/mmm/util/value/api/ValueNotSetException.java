/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.exception.NlsBundleUtilExceptionRoot;

/**
 * This is the exception thrown if a required value was not set.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated use {@link net.sf.mmm.util.exception.api.ValueNotSetException}
 */
@Deprecated
public class ValueNotSetException extends ValueException {

  /** uid for serialization */
  private static final long serialVersionUID = 1L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ValueNotSet";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValueNotSetException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   */
  public ValueNotSetException(Object valueSource) {

    super(createBundle(NlsBundleUtilExceptionRoot.class).errorValueNotSet(valueSource));
  }

  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
