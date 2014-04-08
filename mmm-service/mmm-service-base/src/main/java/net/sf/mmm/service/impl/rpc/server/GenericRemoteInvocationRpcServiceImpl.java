/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.rpc.server;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalCalls;
import net.sf.mmm.service.base.GenericRemoteInvocationTransactionalResults;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcCall;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcRequest;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcResponse;
import net.sf.mmm.service.base.rpc.GenericRemoteInvocationRpcService;
import net.sf.mmm.service.base.server.AbstractGenericRemoteInvocationService;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This class extends {@link AbstractGenericRemoteInvocationRpcService} with transaction support via
 * {@link TransactionExecutor}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(GenericRemoteInvocationRpcService.CDI_NAME)
public class GenericRemoteInvocationRpcServiceImpl extends AbstractGenericRemoteInvocationRpcService {

  /** @see #getTransactionExecutor() */
  private TransactionExecutor transactionExecutor;

  /**
   * The constructor.
   */
  public GenericRemoteInvocationRpcServiceImpl() {

    super();
  }

  /**
   * @return the {@link TransactionExecutor}.
   */
  protected TransactionExecutor getTransactionExecutor() {

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
      final GenericRemoteInvocationTransactionalCalls<GenericRemoteInvocationRpcCall> transactionalCalls,
      AbstractGenericRemoteInvocationService<GenericRemoteInvocationRpcCall, GenericRemoteInvocationRpcRequest, GenericRemoteInvocationRpcResponse, GenericRemoteInvocationRpcCallHandler<?>>.RequestContext context)
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

}
