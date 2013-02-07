/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.lang.reflect.Type;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link NlsClassCastException} is analog to an {@link ClassCastException} but with native language
 * support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsClassCastException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4554379519897968840L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ClassCast";

  /**
   * The constructor.
   * 
   * @param object is the object that can NOT be cast to <code>expectedType</code>.
   * @param expectedType is the expected type the <code>object</code> should have but has not.
   */
  public NlsClassCastException(Object object, Type expectedType) {

    this(null, object, expectedType);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object that can NOT be cast to <code>expectedType</code>.
   * @param expectedType is the expected type the <code>object</code> should have but has not.
   */
  public NlsClassCastException(Throwable nested, Object object, Type expectedType) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorCast(object, getType(object), expectedType));
  }

  /**
   * This method gets the {@link #getClass() class} of an object in a null-safe way.
   * 
   * @param object is the object for which the {@link #getClass() class} is requested. May be
   *        <code>null</code>
   * @return the class reflecting the given <code>object</code> or <code>null</code> if <code>object</code> is
   *         <code>null</code>.
   */
  private static Class<?> getType(Object object) {

    if (object == null) {
      return null;
    } else {
      return object.getClass();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }
}
