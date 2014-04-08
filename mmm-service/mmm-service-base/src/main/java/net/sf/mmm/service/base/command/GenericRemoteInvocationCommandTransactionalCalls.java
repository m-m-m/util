/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.command;

import java.util.List;

import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalCalls;

/**
 * This is the generic transfer-object containing all {@link RemoteInvocationCommand}s that shall be processed
 * in a separate transaction.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationCommandTransactionalCalls extends
    GenericRemoteInvocationTransactionalCalls<RemoteInvocationCommand<?>> {

  /** UID for serialization. */
  private static final long serialVersionUID = -147747512591880199L;

  /** @see #getCalls() */
  private RemoteInvocationCommand<?>[] calls;

  /**
   * The constructor for (de)serialization.
   */
  protected GenericRemoteInvocationCommandTransactionalCalls() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param calls - see {@link #getCalls()}.
   */
  public GenericRemoteInvocationCommandTransactionalCalls(RemoteInvocationCommand<?>... calls) {

    super();
    this.calls = calls;
  }

  /**
   * The constructor.
   * 
   * @param calls - see {@link #getCalls()}.
   */
  public GenericRemoteInvocationCommandTransactionalCalls(List<RemoteInvocationCommand<?>> calls) {

    this(calls.toArray(new RemoteInvocationCommand[calls.size()]));
  }

  /**
   * @return the commands
   */
  @Override
  public RemoteInvocationCommand<?>[] getCalls() {

    return this.calls;
  }

}
