/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.impl.spring;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import net.sf.mmm.transaction.api.TransactionAdapter;
import net.sf.mmm.transaction.api.TransactionSettings;
import net.sf.mmm.transaction.base.AbstractTransactionExecutor;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.transaction.api.TransactionExecutor} interface using
 * {@link PlatformTransactionManager spring-tx}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
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
  @Resource
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
   * This is the implementation of the {@link TransactionAdapter} interface
   * using spring-tx.
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
      this.transactionDefinition = new DefaultTransactionDefinition();
      if (settings.getIsolationLevel() != null) {
        this.transactionDefinition.setIsolationLevel(settings.getIsolationLevel().getJdbcCode());
      }
      if (settings.getTimeout() != null) {
        this.transactionDefinition.setTimeout(settings.getTimeout().intValue());
      }
      start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TransactionStatus createNewTransaction() {

      return getPlatformTransactionManager().getTransaction(this.transactionDefinition);
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

      getPlatformTransactionManager().rollback(getActiveTransaction());
    }

  }

}