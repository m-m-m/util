/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;

/**
 * An {@link ObjectMismatchException} is thrown if an object or value do NOT match as expected.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.2
 */
public class ObjectMismatchException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -6318098527301303965L;

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
   * @param container is the object containing the mismatching <code>object</code>.
   */
  public ObjectMismatchException(Object object, Object expected, Object container) {

    this((Throwable) null, object, expected, container);
  }

  /**
   * The constructor.
   * 
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param container is the object containing the mismatching <code>object</code>.
   * @param property is the property or key of the <code>container</code> containing the mismatching
   *        <code>object</code>.
   */
  public ObjectMismatchException(Object object, Object expected, Object container, Object property) {

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

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorObjectMismatch(object, expected));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param container is the object containing the mismatching <code>object</code>.
   */
  public ObjectMismatchException(Throwable nested, Object object, Object expected, Object container) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorObjectMismatchWithContainer(object, expected,
        container));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param container is the object containing the mismatching <code>object</code>.
   * @param property is the property or key of the <code>container</code> containing the mismatching
   *        <code>object</code>.
   */
  public ObjectMismatchException(Throwable nested, Object object, Object expected, Object container, Object property) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorObjectMismatchWithContainerAndProperty(object,
        expected, container, property));
  }

}
