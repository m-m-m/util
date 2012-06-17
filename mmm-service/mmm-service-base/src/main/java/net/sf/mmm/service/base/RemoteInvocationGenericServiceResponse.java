/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

/**
 * This is the generic transfer-object for the response to a {@link RemoteInvocationGenericServiceRequest}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationGenericServiceResponse implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -3659003282415103857L;

  /** @see #getResults() */
  private RemoteInvocationServiceResult[] results;

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
   * @param results - see {@link #getResults()}.
   */
  public RemoteInvocationGenericServiceResponse(int requestId, RemoteInvocationServiceResult[] results) {

    super();
    this.results = results;
    this.requestId = requestId;
  }

  /**
   * @return the {@link RemoteInvocationGenericServiceRequest#getRequestId() ID of the request} this response
   *         corresponds to.
   */
  public int getRequestId() {

    return this.requestId;
  }

  /**
   * @return an array with the {@link RemoteInvocationServiceResult}s.
   */
  public RemoteInvocationServiceResult[] getResults() {

    return this.results;
  }

}
