/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.command.client;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.client.RemoteInvocationQueueSettings;
import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.api.command.client.RemoteInvocationCommandCaller;
import net.sf.mmm.service.api.command.client.RemoteInvocationCommandQueue;
import net.sf.mmm.service.base.client.AbstractRemoteInvocationCaller;
import net.sf.mmm.service.base.client.RemoteInvocationCallData;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandRequest;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandTransactionalCalls;

/**
 * This is the abstract base implementation of {@link RemoteInvocationCommandCaller}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationCommandCaller extends
    AbstractRemoteInvocationCaller<RemoteInvocationCommandQueue, RemoteInvocationCommand<?>, //
    GenericRemoteInvocationCommandTransactionalCalls, GenericRemoteInvocationCommandRequest> implements
    RemoteInvocationCommandCaller {

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationCommandCaller() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
      Consumer<? extends RESULT> successCallback) {

    callCommand(command, successCallback, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
      Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

    newQueueForAutoCommit().callCommand(command, successCallback, failureCallback);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RemoteInvocationCommandQueue createQueue(RemoteInvocationQueueSettings settings,
      AbstractRemoteInvocationQueue parentQueue) {

    return new RemoteInvocationCommandQueueImpl(settings, parentQueue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationCommandTransactionalCalls createRemoteInvocationTransactionalCalls(
      List<RemoteInvocationCommand<?>> calls) {

    return new GenericRemoteInvocationCommandTransactionalCalls(calls);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationCommandRequest createRequest(int requestId, CsrfToken token,
      List<GenericRemoteInvocationCommandTransactionalCalls> transactionalCalls) {

    return new GenericRemoteInvocationCommandRequest(requestId, token, transactionalCalls);
  }

  /**
   * This is the implementation of {@link RemoteInvocationCommandQueue}.
   */
  protected class RemoteInvocationCommandQueueImpl extends AbstractRemoteInvocationQueue implements
      RemoteInvocationCommandQueue {

    /**
     * The constructor.
     *
     * @param settings - see {@link #getSettings()}.
     * @param parentQueue - see {@link #getParentQueue()}.
     */
    public RemoteInvocationCommandQueueImpl(RemoteInvocationQueueSettings settings,
        AbstractRemoteInvocationQueue parentQueue) {

      super(settings, parentQueue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
        Consumer<? extends RESULT> successCallback) {

      callCommand(command, successCallback, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
        Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback) {

      RemoteInvocationCallData<RESULT, RemoteInvocationCommand<?>> callData = new RemoteInvocationCallData<>(
          successCallback, failureCallback);
      callData.setCall(command);
      addCall(callData);
    }

  }

}
