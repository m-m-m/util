/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

/**
 * This enum contains the available {@link TransactionEvent#getType() types} of
 * {@link TransactionEvent}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum TransactionEventType {

  /**
   * This event type is send if a {@link TransactionExecutor transactional
   * execution} has been started.
   */
  START,

  /**
   * This event type is send after a {@link #COMMIT} if a new transaction has
   * opened in case of an {@link TransactionAdapter#interCommit() intermediate
   * commit}.
   */
  CONTINUE,

  /**
   * This event type is send if a transaction has been committed.
   * 
   * @see TransactionAdapter#commit()
   */
  COMMIT,

  /**
   * This event type is send if a rollback of some transaction was performed.
   * 
   * @see TransactionAdapter#rollback()
   */
  ROLLBACK,

  /**
   * This event type is send if a {@link TransactionExecutor transactional
   * execution} has terminated.
   */
  STOP,

}
