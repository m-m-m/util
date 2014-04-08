/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.command;

/**
 * This is the interface for a generic RPC-service that can
 * {@link #callCommands(GenericRemoteInvocationCommandRequest) call} any
 * {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface GenericRemoteInvocationCommandService {

  /**
   * This method calls one or multiple {@link net.sf.mmm.service.api.command.RemoteInvocationCommand}s on the
   * server. The implementation is typically generic and dispatches the
   * {@link net.sf.mmm.service.api.command.RemoteInvocationCommand commands} to according services.
   * 
   * @param request is the {@link GenericRemoteInvocationCommandRequest}.
   * @return the according {@link GenericRemoteInvocationCommandResponse}.
   */
  GenericRemoteInvocationCommandResponse callCommands(GenericRemoteInvocationCommandRequest request);

}
