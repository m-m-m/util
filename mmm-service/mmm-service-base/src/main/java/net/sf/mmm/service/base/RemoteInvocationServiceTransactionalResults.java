/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

/**
 * This is the generic transfer-object containing all {@link #getResults() results} that have been processed
 * in a separate transaction. As alternative it may contain a {@link #getFailure() failure} if the
 * transactional processing failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationServiceTransactionalResults implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 7613481198181442644L;

  /** @see #getFailure() */
  private Throwable failure;

  /** @see #getResults() */
  private Serializable[] results;

  /**
   * The constructor for (de)serialization.
   */
  protected RemoteInvocationServiceTransactionalResults() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param results - see {@link #getResults()}.
   */
  public RemoteInvocationServiceTransactionalResults(Serializable... results) {

    super();
    this.results = results;
  }

  /**
   * The constructor.
   * 
   * @param failure - see {@link #getFailure()}.
   */
  public RemoteInvocationServiceTransactionalResults(Throwable failure) {

    super();
    this.failure = failure;
  }

  /**
   * @return an array with the results of the {@link RemoteInvocationServiceCall service calls}. Will be
   *         <code>null</code> if {@link #getFailure() failure} is NOT <code>null</code>.
   */
  public Serializable[] getResults() {

    return this.results;
  }

  /**
   * @return the {@link Throwable failure} that occurred during the processing causing the entire transaction
   *         to fail or <code>null</code> if the transaction and invocations have been successful and the
   *         {@link #getResults() results} are valid.
   */
  public Throwable getFailure() {

    return this.failure;
  }

}
