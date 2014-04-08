/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base;

import java.io.Serializable;

import net.sf.mmm.service.api.RemoteInvocationCall;

/**
 * This is the generic transfer-object containing all {@link RemoteInvocationCall}s that shall be processed in
 * a separate transaction.
 * 
 * @param <CALL> is the generic type of the contained {@link RemoteInvocationCall}s.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class GenericRemoteInvocationTransactionalCalls<CALL extends RemoteInvocationCall> implements
    Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = 9149720941942295891L;

  /**
   * The constructor.
   */
  protected GenericRemoteInvocationTransactionalCalls() {

    super();
  }

  /**
   * @return the {@literal <CALL>}-array with the {@link RemoteInvocationCall}s to invoke in a transaction.
   */
  public abstract CALL[] getCalls();

}
