/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.security.api;

/**
 * This exception is thrown if a <em>subject</em> (see e.g. {@link java.security.Principal}) tried to perform
 * an <em>operation</em> on an <code>object</code> without having the appropriate permissions. <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PermissionDeniedException extends SecurityException {

  /** UID for serialization */
  private static final long serialVersionUID = 3256727294587712817L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "PermissionDenied";

  /**
   * The constructor.
   *
   * @param subject is the user that wanted to perform the permitted operation.
   * @param operation is the action that failed due to lack of permissions.
   */
  public PermissionDeniedException(Object subject, Object operation) {

    this(null, subject, operation);
  }

  /**
   * The constructor.
   *
   * @param subject is the user that wanted to perform the permitted operation.
   * @param operation is the action that failed due to lack of permissions.
   * @param object is the object on which the <code>operation</code> should have been performed.
   */
  public PermissionDeniedException(Object subject, Object operation, Object object) {

    this(null, subject, operation, object);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param subject is the user that wanted to perform the permitted operation.
   * @param operation is the action that failed due to lack of permissions.
   */
  public PermissionDeniedException(Throwable nested, Object subject, Object operation) {

    this(nested, subject, operation, null);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param subject is the user that wanted to perform the permitted operation.
   * @param operation is the action that failed due to lack of permissions.
   * @param object is the object on which the <code>operation</code> should have been performed.
   */
  public PermissionDeniedException(Throwable nested, Object subject, Object operation, Object object) {

    super(nested, getBundle().errorPermissionDenied(subject, operation, object));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
