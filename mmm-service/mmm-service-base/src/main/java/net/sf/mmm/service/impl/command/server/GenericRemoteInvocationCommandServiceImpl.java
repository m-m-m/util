/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.command.server;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import net.sf.mmm.service.api.command.RemoteInvocationCommand;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalCalls;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandRequest;
import net.sf.mmm.service.base.command.GenericRemoteInvocationCommandResponse;
import net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This is the implementation of {@link net.sf.mmm.service.base.command.GenericRemoteInvocationCommandService}
 * using {@link TransactionExecutor}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GenericRemoteInvocationCommandServiceImpl extends AbstractGenericRemoteInvocationCommandService {

  /** @see #getTransactionExecutor() */
  private TransactionExecutor transactionExecutor;

  /**
   * The constructor.
   */
  public GenericRemoteInvocationCommandServiceImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.transactionExecutor == null) {
      throw new ResourceMissingException(TransactionExecutor.class.getSimpleName());
    }
  }

  /**
   * @return the {@link TransactionExecutor}.
   */
  public TransactionExecutor getTransactionExecutor() {

    return this.transactionExecutor;
  }

  /**
   * @param transactionExecutor is the {@link TransactionExecutor} to {@link Inject}.
   */
  @Inject
  public void setTransactionExecutor(TransactionExecutor transactionExecutor) {

    getInitializationState().requireNotInitilized();
    this.transactionExecutor = transactionExecutor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericRemoteInvocationTransactionalResults processCallsInTransaction(
      final GenericRemoteInvocationTransactionalCalls<RemoteInvocationCommand<?>> transactionalCalls,
      AbstractGenericRemoteInvocationService<RemoteInvocationCommand<?>, GenericRemoteInvocationCommandRequest, GenericRemoteInvocationCommandResponse, GenericRemoteInvocationCommandCallHandler>.RequestContext context)
      throws Exception {

    final RequestContext requestContext = context;
    Callable<GenericRemoteInvocationTransactionalResults> callable = new Callable<GenericRemoteInvocationTransactionalResults>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public GenericRemoteInvocationTransactionalResults call() {

        GenericRemoteInvocationTransactionalResults results = processTransactionalCalls(transactionalCalls,
            requestContext);
        return results;
      }
    };
    return this.transactionExecutor.doInTransaction(callable);
  }

}
