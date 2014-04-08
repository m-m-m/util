/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.api.command;

import java.io.Serializable;

/**
 * This is the interface for the handler of a specific {@link RemoteInvocationCommand}. Its implementation
 * defines how to {@link #handle(RemoteInvocationCommand) handle} the command on the server-side.
 * 
 * @param <RESULT> is the type of the result of the {@link #handle(RemoteInvocationCommand) invocation}.
 * @param <COMMAND> is the type of the specific {@link RemoteInvocationCommand} this handler is
 *        {@link #getCommandClass() responsible} for.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface RemoteInvocationCommandHandler<RESULT extends Serializable, COMMAND extends RemoteInvocationCommand<RESULT>> {

  /**
   * @return the type of command this handler is responsible for.
   */
  Class<COMMAND> getCommandClass();

  /**
   * This method invokes the given <code>command</code> and returns the result of the invocation.
   * 
   * @param command is the {@link RemoteInvocationCommand} to handle.
   * @return the result of the <code>command</code> invocation.
   */
  RESULT handle(COMMAND command);

}
