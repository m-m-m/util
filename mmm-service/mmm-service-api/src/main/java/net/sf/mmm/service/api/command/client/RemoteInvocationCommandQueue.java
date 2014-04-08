/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.command.client;

import java.io.Serializable;
import java.util.function.Consumer;

import net.sf.mmm.service.api.client.RemoteInvocationQueue;
import net.sf.mmm.service.api.command.RemoteInvocationCommand;

/**
 * This is the interface for a {@link RemoteInvocationQueue remote invocation queue} based on
 * {@link RemoteInvocationCommandCaller}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationCommandQueue extends AbstractRemoteInvocationCommandCaller,
    RemoteInvocationQueue {

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * This will NOT send a technical request to the server unless this queue is {@link #commit() committed}. A
   * queue allows multiple invocations of this method in order to collect remote invocations that shall be
   * send to the server within the same technical request.
   * 
   * @see #setDefaultFailureCallback(Consumer)
   */
  @Override
  <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
      Consumer<? extends RESULT> successCallback);

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * This will NOT send a technical request to the server unless this queue is {@link #commit() committed}. A
   * queue allows multiple invocations of this method in order to collect remote invocations that shall be
   * send to the server within the same technical request.
   */
  @Override
  <RESULT extends Serializable> void callCommand(RemoteInvocationCommand<RESULT> command,
      Consumer<? extends RESULT> successCallback, Consumer<Throwable> failureCallback);

}
