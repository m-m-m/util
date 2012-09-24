/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.impl.jta;

import javax.transaction.Transaction;

import net.sf.mmm.transaction.api.TransactionSettings;
import net.sf.mmm.transaction.base.AbstractTransactionExecutor;
import net.sf.mmm.transaction.base.TransactionCommitException;
import net.sf.mmm.transaction.base.TransactionRollbackException;

/**
 * This is the implementation of the {@link net.sf.mmm.transaction.api.TransactionExecutor} interface using
 * the {@link javax.transaction JTA} (Java Transaction API).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class JtaTransactionExecutor extends AbstractTransactionExecutor {

  /**
   * The constructor.
   */
  public JtaTransactionExecutor() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JtaTransactionAdapter openTransactionAdapter(TransactionSettings settings) {

    return new JtaTransactionAdapter();
  }

  /**
   * This is the implementation of the {@link net.sf.mmm.transaction.api.TransactionAdapter} interface using
   * JTA.
   */
  private class JtaTransactionAdapter extends AbstractTransactionAdapter<Transaction> {

    /**
     * The constructor.
     */
    public JtaTransactionAdapter() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Transaction createNewTransaction() {

      // Retrieve UserTransaction via JNDI Lookup "java:comp/UserTransaction"

      // TODO Auto-generated method stub
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doCommit() {

      try {
        getActiveTransaction().commit();
      } catch (Exception e) {
        throw new TransactionCommitException(e);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doRollback() {

      try {
        getActiveTransaction().rollback();
      } catch (Exception e) {
        throw new TransactionRollbackException(e);
      }
    }

  }

}
