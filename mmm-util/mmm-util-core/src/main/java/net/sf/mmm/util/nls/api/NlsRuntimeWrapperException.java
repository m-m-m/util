/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is an {@link NlsRuntimeException} to wrap an arbitrary {@link NlsThrowable}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class NlsRuntimeWrapperException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -7836201532457130038L;

  /** The original cause as {@link NlsThrowable}. */
  private/* final */NlsThrowable cause;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected NlsRuntimeWrapperException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param e is the {@link #getCause() cause} to wrap.
   */
  public NlsRuntimeWrapperException(AbstractNlsException e) {

    super(e, e.getNlsMessage());
    this.cause = e;
  }

  /**
   * The constructor.
   *
   * @param e is the {@link #getCause() cause} to wrap.
   */
  public NlsRuntimeWrapperException(AbstractNlsRuntimeException e) {

    super(e, e.getNlsMessage());
    this.cause = e;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return this.cause.getCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isForUser() {

    return this.cause.isForUser();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    return this.cause.isTechnical();
  }

}
