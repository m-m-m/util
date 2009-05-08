/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

import java.util.concurrent.Callable;

import net.sf.mmm.util.event.api.EventSource;

/**
 * This is the interface for a component capable of executing your code within a
 * transaction. It is a front-end to an underlying transaction-manager that
 * makes your transactional code easy and less error-prone.<br>
 * Additionally it allows you to add custom hooks to your transaction-management
 * if you perform all transaction operations via this API.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransactionExecutor extends
    EventSource<TransactionEvent, TransactionEventListener> {

  /**
   * This method executes the given <code>callable</code> in the context of a
   * transaction.<br>
   * Therefore this method opens a new transaction, {@link Callable#call()
   * invokes} the <code>callable</code> in a surrounding try-statement so that
   * the transaction is automatically committed if your <code>callable</code>
   * succeeds while a rollback is performed in case it fails (throws anything).
   * 
   * @param <RESULT> is the generic type returned from your
   *        <code>callable</code>.
   * @param callable is the {@link Callable} to be {@link Callable#call()
   *        executed} within a transaction.
   * @return the result of the <code>callable</code>.
   * @throws Exception if the <code>callable</code> failed.
   */
  <RESULT> RESULT doInTransaction(Callable<RESULT> callable) throws Exception;

  /**
   * This method executes the given <code>callable</code> in the context of a
   * transaction.<br>
   * Therefore this method opens a new transaction, {@link Callable#call()
   * invokes} the <code>callable</code> in a surrounding try-statement so that
   * the transaction is automatically committed if your <code>callable</code>
   * succeeds while a rollback is performed in case it fails (throws anything).
   * 
   * @param <RESULT> is the generic type returned from your
   *        <code>callable</code>.
   * @param callable is the {@link Callable} to be {@link Callable#call()
   *        executed} within a transaction.
   * @param settings are the settings to configure transactions.
   * @return the result of the <code>callable</code>.
   * @throws Exception if the <code>callable</code> failed.
   */
  <RESULT> RESULT doInTransaction(Callable<RESULT> callable, TransactionSettings settings)
      throws Exception;

  /**
   * This method executes the given <code>callable</code> in the context of a
   * transaction.<br>
   * Therefore this method opens a new transaction,
   * {@link TransactionCallable#call(TransactionAdapter) invokes} the
   * <code>callable</code> in a surrounding try-statement so that the
   * transaction is automatically committed if your <code>callable</code>
   * succeeds while a rollback is performed in case it fails (throws anything).
   * 
   * 
   * @param <RESULT> is the generic type returned from your
   *        <code>callable</code>.
   * @param <EXCEPTION> is the generic type of the exception potentially thrown.
   * @param callable is the {@link Callable} to be {@link Callable#call()
   *        executed} within a transaction.
   * @return the result of the <code>callable</code>.
   * @throws EXCEPTION
   * @throws Exception if the <code>callable</code> failed.
   */
  <RESULT, EXCEPTION extends Exception> RESULT doInTransaction(
      TransactionCallable<RESULT, EXCEPTION> callable) throws EXCEPTION;

  /**
   * This method executes the given <code>callable</code> in the context of a
   * transaction.<br>
   * Therefore this method opens a new transaction,
   * {@link TransactionCallable#call(TransactionAdapter) invokes} the
   * <code>callable</code> in a surrounding try-statement so that the
   * transaction is automatically committed if your <code>callable</code>
   * succeeds while a rollback is performed in case it fails (throws anything).
   * 
   * 
   * @param <RESULT> is the generic type returned from your
   *        <code>callable</code>.
   * @param <EXCEPTION> is the generic type of the exception potentially thrown.
   * @param callable is the {@link Callable} to be {@link Callable#call()
   *        executed} within a transaction.
   * @param settings are the settings to configure transactions.
   * @return the result of the <code>callable</code>.
   * @throws EXCEPTION
   * @throws Exception if the <code>callable</code> failed.
   */
  <RESULT, EXCEPTION extends Exception> RESULT doInTransaction(
      TransactionCallable<RESULT, EXCEPTION> callable, TransactionSettings settings)
      throws EXCEPTION;

}
