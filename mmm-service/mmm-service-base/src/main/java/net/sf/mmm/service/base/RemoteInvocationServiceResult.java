/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

import net.sf.mmm.service.api.RemoteInvocationServiceContext;

/**
 * This is the generic transfer-object for the result of an {@link RemoteInvocationServiceCall}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class RemoteInvocationServiceResult implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 4058452786590591296L;

  /** @see #getFailure() */
  private Throwable failure;

  /** @see #getResult() */
  private Serializable result;

  /** @see #getContext() */
  private RemoteInvocationServiceContext context;

  /**
   * The constructor for (de)serialization.
   */
  protected RemoteInvocationServiceResult() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param failure - see {@link #getFailure()}.
   * @param context - see {@link #getContext()}.
   */
  public RemoteInvocationServiceResult(Throwable failure, RemoteInvocationServiceContext context) {

    super();
    this.failure = failure;
    this.context = context;
  }

  /**
   * The constructor.
   * 
   * @param result - see {@link #getResult()}.
   * @param context - see {@link #getContext()}.
   */
  public RemoteInvocationServiceResult(Serializable result, RemoteInvocationServiceContext context) {

    super();
    this.result = result;
  }

  /**
   * @return the failure
   */
  public Throwable getFailure() {

    return this.failure;
  }

  /**
   * @return the result
   */
  public Serializable getResult() {

    return this.result;
  }

  /**
   * @return the context
   */
  public RemoteInvocationServiceContext getContext() {

    return this.context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    int hash = 0;
    if (this.failure != null) {
      hash = this.failure.hashCode();
    }
    if (this.result != null) {
      hash = this.result.hashCode();
    }
    return -hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    RemoteInvocationServiceResult other = (RemoteInvocationServiceResult) obj;
    if (this.failure == null) {
      if (other.failure != null) {
        return false;
      }
    } else if (!this.failure.equals(other.failure)) {
      return false;
    }
    if (this.result == null) {
      if (other.result != null) {
        return false;
      }
    } else if (!this.result.equals(other.result)) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.result != null) {
      return this.result.toString();
    } else if (this.failure != null) {
      return this.failure.toString();
    } else {
      return "null";
    }
  }

}
