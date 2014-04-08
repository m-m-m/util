/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.rpc;

import java.util.Collection;

import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalCalls;

/**
 * This is the generic transfer-object containing all {@link GenericRemoteInvocationRpcCall invocation calls}
 * that shall be processed in a separate transaction.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationRpcTransactionalCalls extends
    GenericRemoteInvocationTransactionalCalls<GenericRemoteInvocationRpcCall> {

  /** UID for serialization. */
  private static final long serialVersionUID = 4382650741266686356L;

  /** @see #getCalls() */
  private GenericRemoteInvocationRpcCall[] calls;

  /**
   * The constructor for (de)serialization.
   */
  protected GenericRemoteInvocationRpcTransactionalCalls() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param calls - see {@link #getCalls()}.
   */
  public GenericRemoteInvocationRpcTransactionalCalls(GenericRemoteInvocationRpcCall... calls) {

    super();
    this.calls = calls;
  }

  /**
   * The constructor.
   * 
   * @param calls - see {@link #getCalls()}.
   */
  public GenericRemoteInvocationRpcTransactionalCalls(Collection<GenericRemoteInvocationRpcCall> calls) {

    this(calls.toArray(new GenericRemoteInvocationRpcCall[calls.size()]));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericRemoteInvocationRpcCall[] getCalls() {

    return this.calls;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getClass().getSimpleName() + "@" + this.calls.length;
  }

}
