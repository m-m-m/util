/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * An {@link ObjectDisposedException} is thrown if an object or value is already
 * {@link net.sf.mmm.util.lang.api.attribute.AttributeReadDisposed#isDisposed() disposed} and therefore an
 * operation failed that has been invoked on it.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ObjectDisposedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6525822430606725507L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "Disposed";

  /**
   * The constructor.
   * 
   * @param object is the object (or an identifier of the object) that is
   *        {@link net.sf.mmm.util.lang.api.attribute.AttributeReadDisposed#isDisposed() disposed}.
   */
  public ObjectDisposedException(Object object) {

    super(createBundle(NlsBundleUtilCoreRoot.class).errorObjectDisposed(object));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
