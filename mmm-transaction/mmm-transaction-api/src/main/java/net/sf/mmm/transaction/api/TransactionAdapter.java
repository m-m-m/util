/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

/**
 * This interface adapts underlying transactions.
 * 
 * @see TransactionCallable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransactionAdapter {

  /**
   * This method {@link #commit() commits} the transaction currently active and
   * opens a new one. You should use this method for (recoverable) batches that
   * process large sets of data and therefore would overload if everything was
   * done in one technical transaction.
   */
  void interCommit();

  /**
   * This method commits the current transaction without opening a new
   * transaction. It allows to throw an exception in your
   * {@link TransactionCallable callable} but end with a commit rather than a
   * {@link #rollback()}. <br>
   * <b>ATTENTION:</b><br>
   * Please only use this method as end-user if there is no other way to solve
   * your problem.
   */
  void commit();

  /**
   * This method performs a rollback of the current transaction. It allows to
   * return from your {@link TransactionCallable callable} without
   * {@link #commit() committing} and or throwing an exception. <br>
   * <b>ATTENTION:</b><br>
   * Please only use this method as end-user if there is no other way to solve
   * your problem.
   */
  void rollback();

  /**
   * This method determines if this context holds an active transaction. <br>
   * This is the case until {@link #commit()} or {@link #rollback()} was called
   * explicitly.
   * 
   * @return <code>true</code> if there is an active transaction or
   *         <code>false</code> otherwise.
   */
  boolean isActive();

  /**
   * This method gets the {@link TransactionContext} that allows to add
   * individual data to be associated with this {@link TransactionAdapter}.
   * 
   * @return the {@link TransactionContext}.
   */
  TransactionContext getContext();

}
