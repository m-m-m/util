/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.base.command.server;

import java.io.Serializable;

import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.api.command.RemoteInvocationCommandHandler;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation for a {@link RemoteInvocationCommandHandler}. It is recommended
 * that your server-side implementations of {@link RemoteInvocationCommandHandler}s are derived from this
 * class (however, this is technically not required).
 *
 * @param <RESULT> is the type of the result of the {@link #handle(RemoteInvocationCommand) invocation}.
 * @param <COMMAND> is the type of the specific {@link RemoteInvocationCommand} this handler is
 *        {@link #getCommandClass() responsible} for.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractRemoteInvocationCommandHandler<RESULT extends Serializable, COMMAND extends RemoteInvocationCommand<RESULT>>
    extends AbstractLoggableComponent implements RemoteInvocationCommandHandler<RESULT, COMMAND> {

  /**
   * The constructor.
   */
  public AbstractRemoteInvocationCommandHandler() {

    super();
  }

}
