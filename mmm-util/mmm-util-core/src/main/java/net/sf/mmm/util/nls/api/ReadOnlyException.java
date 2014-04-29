/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link ReadOnlyException} is thrown if the modification of something failed because it is read-only. Here
 * something can be the property of a java object, an attribute in a persistent store, a file, etc.<br/>
 * <b>ATTENTION:</b><br>
 * Please design your APIs in a way to prevent such exception where ever possible. However for generic access
 * to objects that can be mutable or read-only this exception is the right choice.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ReadOnlyException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -4118721668441129491L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "ReadOnly";

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ReadOnlyException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param object is the object that is read-only and can not be modified.
   */
  public ReadOnlyException(Object object) {

    this(null, object);
  }

  /**
   * The constructor.
   *
   * @param object is the object that is read-only and can not be modified.
   * @param attribute is the attribute of <code>object</code> that can not be modified.
   *
   * @since 3.1.0
   */
  public ReadOnlyException(Object object, Object attribute) {

    this(null, object, attribute);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object that is read-only and can not be modified.
   */
  public ReadOnlyException(Throwable nested, Object object) {

    this(nested, object, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is the object that is read-only and can not be modified.
   * @param attribute is the attribute of <code>object</code> that can not be modified.
   *
   * @since 3.1.0
   */
  public ReadOnlyException(Throwable nested, Object object, Object attribute) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorReadOnly(object, attribute));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
