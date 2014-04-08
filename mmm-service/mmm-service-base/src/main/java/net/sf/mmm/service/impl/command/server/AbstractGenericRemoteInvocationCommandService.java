/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.command.server;

import java.util.List;

import javax.inject.Inject;

import net.sf.mmm.service.api.CsrfToken;
import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.api.command.RemoteInvocationCommandHandler;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandRequest;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandResponse;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandService;
import net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService;
import net.sf.mmm.util.component.api.AlreadyInitializedException;

/**
 * This is the abstract base implementation of {@link GenericRemoteInvocationCommandService}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractGenericRemoteInvocationCommandService extends
    AbstractGenericRemoteInvocationService<RemoteInvocationCommand<?>, GenericRemoteInvocationCommandRequest, //
    GenericRemoteInvocationCommandResponse, GenericRemoteInvocationCommandCallHandler> implements
    GenericRemoteInvocationCommandService {

  /**
   * The constructor.
   */
  public AbstractGenericRemoteInvocationCommandService() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericRemoteInvocationCommandResponse callCommands(GenericRemoteInvocationCommandRequest request) {

    return processRequest(request);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationCommandResponse createResponse(int requestId, CsrfToken xsrfToken,
      GenericRemoteInvocationTransactionalResults[] transactionalResults) {

    return new GenericRemoteInvocationCommandResponse(requestId, xsrfToken, transactionalResults);
  }

  /**
   * @param commandHandlers is the {@link List} with the supported {@link RemoteInvocationCommandHandler}s.
   */
  @Inject
  public void setCommandHandlers(List<RemoteInvocationCommandHandler<?, ?>> commandHandlers) {

    if (getHandlerCount() > 0) {
      throw new AlreadyInitializedException();
    }
    for (RemoteInvocationCommandHandler<?, ?> handler : commandHandlers) {
      GenericRemoteInvocationCommandCallHandler adapter = new GenericRemoteInvocationCommandCallHandler(this, handler);
      registerHandler(adapter);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getHandlerId(RemoteInvocationCommand<?> command) {

    return command.getClass().getName();
  }

}
