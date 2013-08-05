/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.service.base.RemoteInvocationGenericService;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalCalls;
import net.sf.mmm.service.base.RemoteInvocationServiceTransactionalResults;
import net.sf.mmm.transaction.api.TransactionAdapter;
import net.sf.mmm.transaction.api.TransactionCallable;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This class extends {@link AbstractRemoteInvocationGenericServiceImpl} with transaction support via
 * {@link TransactionExecutor}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(RemoteInvocationGenericService.CDI_NAME)
public class RemoteInvocationGenericServiceImpl extends AbstractRemoteInvocationGenericServiceImpl {

  /** @see #getTransactionExecutor() */
  private TransactionExecutor transactionExecutor;

  /**
   * The constructor.
   */
  public RemoteInvocationGenericServiceImpl() {

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
  protected RemoteInvocationServiceTransactionalResults callServicesInTransaction(
      final RemoteInvocationServiceTransactionalCalls transactionalCalls) {

    TransactionCallable<RemoteInvocationServiceTransactionalResults> callable = new TransactionCallable<RemoteInvocationServiceTransactionalResults>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public RemoteInvocationServiceTransactionalResults call(TransactionAdapter transactionContext) {

        RemoteInvocationServiceTransactionalResults results = callServicesTransactionalCalls(transactionalCalls);
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
