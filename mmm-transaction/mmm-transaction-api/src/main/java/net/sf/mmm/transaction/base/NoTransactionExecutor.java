/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.base;

import net.sf.mmm.transaction.api.TransactionSettings;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NoTransactionExecutor extends AbstractTransactionExecutor {

  /**
   * The constructor.
   */
  public NoTransactionExecutor() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractTransactionAdapter<?> openTransactionAdapter(TransactionSettings settings) {

    return new NoTransactionAdapter();
  }

  /**
   * A dummy transaction adapter.
   */
  private class NoTransactionAdapter extends AbstractTransactionAdapter<Void> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void createNewTransaction() {

      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCommit() {

      // nothing to do...
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRollback() {

      // nothing to do...
    }

  }

}
