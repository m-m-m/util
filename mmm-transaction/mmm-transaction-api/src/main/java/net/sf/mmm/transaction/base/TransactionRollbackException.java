/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.base;

import net.sf.mmm.transaction.NlsBundleTransaction;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if a
 * {@link net.sf.mmm.transaction.api.TransactionAdapter transaction} could NOT
 * be {@link net.sf.mmm.transaction.api.TransactionAdapter#rollback() rolled
 * back}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TransactionRollbackException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 2768552110346253232L;

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public TransactionRollbackException(Throwable nested) {

    super(NlsBundleTransaction.ERR_TRANSACTION_ROLLBACK);
  }

}
