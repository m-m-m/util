/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.command.server;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;

import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.api.command.RemoteInvocationCommandHandler;
import net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService;
import net.sf.mmm.service.base.server.GenericRemoteInvocationCallHandler;
import net.sf.mmm.util.reflect.api.Signature;

/**
 * This is the implementation of {@link GenericRemoteInvocationCallHandler} for a
 * {@link RemoteInvocationCommand}. It simply delegates to a {@link RemoteInvocationCommandHandler}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationCommandCallHandler extends
    GenericRemoteInvocationCallHandler<RemoteInvocationCommand<?>> {

  /** The adapted {@link RemoteInvocationCommandHandler}. */
  private final RemoteInvocationCommandHandler<?, ?> handler;

  /**
   * The implementation {@link RemoteInvocationCommandHandler#handle(RemoteInvocationCommand)} {@link Method}.
   */
  private Method handleMethod;

  /**
   * The constructor.
   *
   * @param genericService - see {@link #getGenericService()}.
   * @param handler is the actual {@link RemoteInvocationCommandHandler} to adapt.
   */
  public GenericRemoteInvocationCommandCallHandler(AbstractGenericRemoteInvocationService<?, ?, ?, ?> genericService,
      RemoteInvocationCommandHandler<?, ?> handler) {

    super(handler.getCommandClass().getName(), genericService);
    this.handler = handler;
    this.handleMethod = detectHandleMethod(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Method getServiceMethod() {

    return this.handleMethod;
  }

  /**
   * Detects the implementation of {@link RemoteInvocationCommandHandler#handle(RemoteInvocationCommand)}.
   *
   * @param commandHandler is the {@link RemoteInvocationCommandHandler} implementation instance.
   * @return the detected handle {@link Method}.
   */
  private Method detectHandleMethod(RemoteInvocationCommandHandler<?, ?> commandHandler) {

    Signature interfaceSignature = new Signature(RemoteInvocationCommand.class);
    for (Method method : commandHandler.getClass().getMethods()) {
      if ("handle".equals(method.getName())) {
        Signature signature = new Signature(method);
        if (interfaceSignature.isApplicable(signature)) {
          return method;
        }
      }
    }
    throw new IllegalStateException("No handle method found!");
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Serializable invoke(RemoteInvocationCommand<?> command) throws Exception {

    return ((RemoteInvocationCommandHandler) this.handler).handle(command);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Set<? extends ConstraintViolation<?>> doValidate(RemoteInvocationCommand<?> command) {

    return getGenericService().getValidator().validate(command);
  }
}
