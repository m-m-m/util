/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

/**
 * This is the generic transfer-object for a request sent from client to server for any number of
 * {@link #getCalls() invocations} to a {@link net.sf.mmm.service.api.RemoteInvocationService}.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public class RemoteInvocationGenericServiceRequest implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -3858810181904299545L;

  /** @see #getCalls() */
  private RemoteInvocationServiceCall[] calls;

  /** @see #getRequestId() */
  private int requestId;

  /**
   * The constructor.
   */
  protected RemoteInvocationGenericServiceRequest() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param requestId - see {@link #getRequestId()}.
   * @param calls - see {@link #getCalls()}.
   */
  public RemoteInvocationGenericServiceRequest(int requestId, RemoteInvocationServiceCall... calls) {

    super();
    this.requestId = requestId;
    this.calls = calls;
  }

  /**
   * @return the identifier for this request. This may be a simple counter from an individual client and may
   *         NOT be unique across multiple clients.
   */
  public int getRequestId() {

    return this.requestId;
  }

  /**
   * @return an array with the {@link RemoteInvocationServiceCall}s (invocations).
   */
  public RemoteInvocationServiceCall[] getCalls() {

    return this.calls;
  }

}
