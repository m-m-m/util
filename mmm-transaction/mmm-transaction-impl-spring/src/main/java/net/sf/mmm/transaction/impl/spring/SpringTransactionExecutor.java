/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.impl.spring;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.transaction.api.TransactionSettings;
import net.sf.mmm.transaction.base.AbstractTransactionExecutor;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.transaction.api.TransactionExecutor} interface using
 * {@link PlatformTransactionManager spring-transaction}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class SpringTransactionExecutor extends AbstractTransactionExecutor {

  /** @see #getPlatformTransactionManager() */
  private PlatformTransactionManager platformTransactionManager;

  /**
   * The constructor.
   */
  public SpringTransactionExecutor() {

    super();
  }

  /**
   * @return the platformTransactionManager
   */
  protected PlatformTransactionManager getPlatformTransactionManager() {

    return this.platformTransactionManager;
  }

  /**
   * @param platformTransactionManager is the platformTransactionManager to set
   */
  @Inject
  public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {

    this.platformTransactionManager = platformTransactionManager;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected SpringTransactionAdapter openTransactionAdapter(TransactionSettings settings) {

    return new SpringTransactionAdapter(settings);
  }

  /**
   * This is the implementation of the
   * {@link net.sf.mmm.transaction.api.TransactionAdapter} interface using
   * spring-tx.
   */
  private class SpringTransactionAdapter extends AbstractTransactionAdapter<TransactionStatus> {

    /** @see #createNewTransaction() */
    private final DefaultTransactionDefinition transactionDefinition;

    /**
     * The constructor.
     * 
     * @param settings are the settings to use.
     */
    public SpringTransactionAdapter(TransactionSettings settings) {

      super();
      this.transactionDefinition = new DefaultTransactionDefinition(
          TransactionDefinition.PROPAGATION_REQUIRED);

      if (settings.getIsolationLevel() != null) {
        this.transactionDefinition.setIsolationLevel(settings.getIsolationLevel().getJdbcCode());
      }
      if (settings.getTimeout() != null) {
        this.transactionDefinition.setTimeout(settings.getTimeout().intValue());
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TransactionStatus createNewTransaction() {

      TransactionStatus tx = getPlatformTransactionManager().getTransaction(
          this.transactionDefinition);
      return tx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCommit() {

      getPlatformTransactionManager().commit(getActiveTransaction());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRollback() {

      TransactionStatus transaction = getActiveTransaction();
      if (transaction.isCompleted()) {
        getLogger().error(
            "Internal error in spring transaction: transaction completed but commit failed!");
      } else {
        getPlatformTransactionManager().rollback(transaction);
      }
    }

  }

}
