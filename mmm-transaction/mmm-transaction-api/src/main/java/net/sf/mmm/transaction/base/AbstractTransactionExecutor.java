/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.base;

import java.util.concurrent.Callable;

import net.sf.mmm.transaction.api.TransactionAdapter;
import net.sf.mmm.transaction.api.TransactionCallable;
import net.sf.mmm.transaction.api.TransactionContext;
import net.sf.mmm.transaction.api.TransactionEvent;
import net.sf.mmm.transaction.api.TransactionEventListener;
import net.sf.mmm.transaction.api.TransactionEventType;
import net.sf.mmm.transaction.api.TransactionExecutor;
import net.sf.mmm.transaction.api.TransactionSettings;
import net.sf.mmm.util.event.base.AbstractEventSource;

/**
 * This is the abstract base implementation of the {@link TransactionExecutor}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractTransactionExecutor extends
    AbstractEventSource<TransactionEvent, TransactionEventListener> implements TransactionExecutor {

  /** @see #getDefaultSettings() */
  private TransactionSettings defaultSettings;

  /**
   * The constructor.
   */
  public AbstractTransactionExecutor() {

    super();
  }

  /**
   * This method gets the default used if no {@link TransactionSettings} are
   * specified explicitly on a call of <code>doInTransaction</code>.
   * 
   * @return the default settings.
   */
  protected TransactionSettings getDefaultSettings() {

    return this.defaultSettings;
  }

  /**
   * This method sets the {@link #getDefaultSettings() default settings}.
   * 
   * @param settings are the settings to set.
   */
  public void setDefaultSettings(TransactionSettings settings) {

    getInitializationState().requireNotInitilized();
    this.defaultSettings = settings;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.defaultSettings == null) {
      this.defaultSettings = new TransactionSettings();
    }
  }

  /**
   * This method opens a new {@link TransactionAdapter}.
   * 
   * @param settings are the according {@link TransactionSettings}.
   * 
   * @return the new {@link TransactionAdapter}.
   */
  protected abstract AbstractTransactionAdapter<?> openTransactionAdapter(
      TransactionSettings settings);

  /**
   * This method create the {@link TransactionContext} instances. Override to
   * change fabrication.
   * 
   * @return a new {@link TransactionContext} instance.
   */
  protected TransactionContext createTransactionContext() {

    return new TransactionContextImpl();
  }

  /**
   * {@inheritDoc}
   */
  public <RESULT> RESULT doInTransaction(Callable<RESULT> callable) throws Exception {

    return doInTransaction(callable, getDefaultSettings());
  }

  /**
   * {@inheritDoc}
   */
  public <RESULT> RESULT doInTransaction(Callable<RESULT> callable, TransactionSettings settings)
      throws Exception {

    AbstractTransactionAdapter<?> transactionAdapter = openTransactionAdapter(settings);
    transactionAdapter.start();
    try {
      RESULT result = callable.call();
      if (transactionAdapter.isActive()) {
        transactionAdapter.commit();
      }
      return result;
    } finally {
      transactionAdapter.stop();
    }
  }

  /**
   * {@inheritDoc}
   */
  public <RESULT, EXCEPTION extends Exception> RESULT doInTransaction(
      TransactionCallable<RESULT, EXCEPTION> callable) throws EXCEPTION {

    return doInTransaction(callable, getDefaultSettings());
  }

  /**
   * {@inheritDoc}
   */
  public <RESULT, EXCEPTION extends Exception> RESULT doInTransaction(
      TransactionCallable<RESULT, EXCEPTION> callable, TransactionSettings settings)
      throws EXCEPTION {

    AbstractTransactionAdapter<?> transactionAdapter = openTransactionAdapter(settings);
    transactionAdapter.start();

    try {
      RESULT result = callable.call(transactionAdapter);
      if (transactionAdapter.isActive()) {
        transactionAdapter.commit();
      }
      return result;
    } finally {
      transactionAdapter.stop();
    }
  }

  /**
   * This is the abstract base implementation of the {@link TransactionAdapter}
   * interface.
   * 
   * @param <TRANSACTION> is the generic type of the underlying native
   *        transaction.
   */
  protected abstract class AbstractTransactionAdapter<TRANSACTION> implements TransactionAdapter {

    /** @see #getContext() */
    private final TransactionContext context;

    /** @see #getActiveTransaction() */
    private TRANSACTION activeTransaction;

    /**
     * The constructor.
     */
    public AbstractTransactionAdapter() {

      super();
      this.context = createTransactionContext();
    }

    /**
     * This method has to be called after construction in order to start the
     * transaction.
     */
    protected void start() {

      this.activeTransaction = createNewTransaction();
      fireEvent(new TransactionEventImpl(TransactionEventType.START, getContext()));
    }

    /**
     * This method has to be called at the end of the transactional execution to
     * end transaction.
     */
    protected void stop() {

      if (isActive()) {
        rollback();
      }
      fireEvent(new TransactionEventImpl(TransactionEventType.STOP, getContext()));
    }

    /**
     * This method creates a new technical transaction.
     * 
     * @return the new native transaction.
     */
    protected abstract TRANSACTION createNewTransaction();

    /**
     * This method gets the underlying native transaction that is currently
     * {@link #isActive() active}.
     * 
     * @return the active transaction.
     * @throws TransactionNotActiveException if there is no {@link #isActive()
     *         active} transaction.
     */
    protected TRANSACTION getActiveTransaction() {

      if (this.activeTransaction == null) {
        throw new TransactionNotActiveException();
      }
      return this.activeTransaction;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isActive() {

      return (this.activeTransaction != null);
    }

    /**
     * {@inheritDoc}
     */
    public TransactionContext getContext() {

      return this.context;
    }

    /**
     * {@inheritDoc}
     */
    public void commit() {

      doCommit();
      this.activeTransaction = null;
      fireEvent(new TransactionEventImpl(TransactionEventType.COMMIT, getContext()));
    }

    /**
     * {@inheritDoc}
     */
    public void interCommit() {

      commit();
      this.activeTransaction = createNewTransaction();
      fireEvent(new TransactionEventImpl(TransactionEventType.CONTINUE, getContext()));
    }

    /**
     * This method performs the actual {@link #commit()} on the
     * {@link #getActiveTransaction() active transaction}.
     */
    protected abstract void doCommit();

    /**
     * {@inheritDoc}
     */
    public void rollback() {

      doRollback();
      this.activeTransaction = null;
      fireEvent(new TransactionEventImpl(TransactionEventType.ROLLBACK, getContext()));
    }

    /**
     * This method performs the actual {@link #rollback()} on the
     * {@link #getActiveTransaction() active transaction}.
     */
    protected abstract void doRollback();

  }

}
