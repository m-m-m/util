/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;
import java.util.Collection;

/**
 * This is the generic transfer-object for a request sent from client to server for any number of
 * {@link #getTransactionalCalls() transactional calls} to a
 * {@link net.sf.mmm.service.api.RemoteInvocationService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RemoteInvocationGenericServiceRequest implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -3858810181904299545L;

  /** @see #getTransactionalCalls() */
  private RemoteInvocationServiceTransactionalCalls[] transactionalCalls;

  /** @see #getRequestId() */
  private int requestId;

  /**
   * The constructor for (de)serialization.
   */
  protected RemoteInvocationGenericServiceRequest() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param transactionalCalls - see {@link #getTransactionalCalls()}.
   */
  public RemoteInvocationGenericServiceRequest(int requestId,
      RemoteInvocationServiceTransactionalCalls... transactionalCalls) {

    super();
    this.requestId = requestId;
    this.transactionalCalls = transactionalCalls;
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param transactionalCalls - see {@link #getTransactionalCalls()}.
   */
  public RemoteInvocationGenericServiceRequest(int requestId,
      Collection<RemoteInvocationServiceTransactionalCalls> transactionalCalls) {

    this(requestId, transactionalCalls
        .toArray(new RemoteInvocationServiceTransactionalCalls[transactionalCalls.size()]));
  }

  /**
   * @return the identifier for this request. This may be a simple counter from an individual client and may
   *         NOT be unique across multiple clients.
   */
  public int getRequestId() {

    return this.requestId;
  }

  /**
   * @return an array with the {@link RemoteInvocationServiceTransactionalCalls}s.
   */
  public RemoteInvocationServiceTransactionalCalls[] getTransactionalCalls() {

    return this.transactionalCalls;
  }

}
