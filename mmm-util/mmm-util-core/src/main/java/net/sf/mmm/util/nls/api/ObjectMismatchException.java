/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

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

    super(NlsBundleUtilCore.ERR_OBJECT_MISMATCH, toMap(KEY_OBJECT, object, KEY_EXPECTED, expected));
  }

  /**
   * The constructor.
   * 
   * @param object is the object (value) that does not match as expected.
   * @param expected is the expected object (value).
   * @param container is the object containing the mismatching <code>object</code>.
   */
  public ObjectMismatchException(Object object, Object expected, Object container) {

    super(NlsBundleUtilCore.ERR_OBJECT_MISMATCH_WITH_CONTAINER, toMap(KEY_OBJECT, object, KEY_EXPECTED, expected,
        KEY_CONTAINER, container));
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

    super(NlsBundleUtilCore.ERR_OBJECT_MISMATCH_WITH_CONTAINER_AND_PROPERTY, addToMap(
        toMap(KEY_OBJECT, object, KEY_EXPECTED, expected, KEY_CONTAINER, container), KEY_PROPERTY, property));
  }

}
