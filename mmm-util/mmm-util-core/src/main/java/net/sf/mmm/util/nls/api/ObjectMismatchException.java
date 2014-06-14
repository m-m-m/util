/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * An {@link ObjectMismatchException} is thrown if an object or value do NOT match as expected.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 * @deprecated use {@link net.sf.mmm.util.exception.api.ObjectMismatchException} instead.
 */
@Deprecated
public class ObjectMismatchException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -6318098527301303965L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "Mismatch";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ObjectMismatchException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   */
  public ObjectMismatchException(Object object, Object expected) {

    this((Throwable) null, object, expected);
  }

  /**
   * The constructor.
   *
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param source is the source of the mismatching <code>object</code>.
   */
  public ObjectMismatchException(Object object, Object expected, Object source) {

    this((Throwable) null, object, expected, source);
  }

  /**
   * The constructor.
   *
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param source is the source of the mismatching <code>object</code>.
   * @param property is the property or key of the <code>container</code> containing the mismatching
   *        <code>object</code>.
   */
  public ObjectMismatchException(Object object, Object expected, Object source, Object property) {

    this((Throwable) null, object, expected, property);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   */
  public ObjectMismatchException(Throwable nested, Object object, Object expected) {

    this(nested, object, expected, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param source is the source of the mismatching <code>object</code>.
   */
  public ObjectMismatchException(Throwable nested, Object object, Object expected, Object source) {

    this(nested, object, expected, source, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param source is the source of the mismatching <code>object</code>.
   * @param property is the property or key of the <code>container</code> containing the mismatching
   *        <code>object</code>.
   */
  public ObjectMismatchException(Throwable nested, Object object, Object expected, Object source, Object property) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorObjectMismatch(object, expected, source, property));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
