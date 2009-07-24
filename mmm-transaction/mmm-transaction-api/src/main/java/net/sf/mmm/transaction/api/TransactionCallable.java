/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

/**
 * This interface is like a {@link java.util.concurrent.Callable} but gets a
 * {@link TransactionAdapter} as argument in order to do
 * {@link TransactionAdapter#interCommit() intermediate commits} (e.g. for
 * batches).
 * 
 * @param <RESULT> the generic type of the returned object. Use {@link Void} to
 *        return nothing (<code>null</code>).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransactionCallable<RESULT> {

  /**
   * Computes a result, or throws an exception if unable to do so.
   * 
   * @param transactionContext is the {@link TransactionAdapter}.
   * @return computed result.
   */
  RESULT call(TransactionAdapter transactionContext);

}
