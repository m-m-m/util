/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;
import java.util.Collection;

/**
 * This is the generic transfer-object for the response to a {@link RemoteInvocationGenericServiceRequest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationGenericServiceResponse implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -3659003282415103857L;

  /** @see #getTransactionalResults() */
  private RemoteInvocationServiceTransactionalResults[] transactionalResults;

  /** @see #getRequestId() */
  private int requestId;

  /**
   * The constructor.
   */
  protected RemoteInvocationGenericServiceResponse() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public RemoteInvocationGenericServiceResponse(int requestId,
      RemoteInvocationServiceTransactionalResults[] transactionalResults) {

    super();
    this.requestId = requestId;
    this.transactionalResults = transactionalResults;
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param transactionalResults - see {@link #getTransactionalResults()}.
   */
  public RemoteInvocationGenericServiceResponse(int requestId,
      Collection<RemoteInvocationServiceTransactionalResults> transactionalResults) {

    this(requestId, transactionalResults.toArray(new RemoteInvocationServiceTransactionalResults[transactionalResults
        .size()]));
  }

  /**
   * @return the {@link RemoteInvocationGenericServiceRequest#getRequestId() ID of the request} this response
   *         corresponds to.
   */
  public int getRequestId() {

    return this.requestId;
  }

  /**
   * @return an array with the {@link RemoteInvocationServiceTransactionalResults}s.
   */
  public RemoteInvocationServiceTransactionalResults[] getTransactionalResults() {

    return this.transactionalResults;
  }

}
