/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.base;

import net.sf.mmm.transaction.NlsBundleTransaction;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if a
 * {@link net.sf.mmm.transaction.api.TransactionAdapter transaction} should be
 * closed but none is
 * {@link net.sf.mmm.transaction.api.TransactionAdapter#isActive() active}.
 * 
 * @see net.sf.mmm.transaction.api.TransactionAdapter#commit()
 * @see net.sf.mmm.transaction.api.TransactionAdapter#rollback()
 * @see net.sf.mmm.transaction.api.TransactionAdapter#interCommit()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TransactionNotActiveException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5210691076548551174L;

  /**
   * The constructor.
   */
  public TransactionNotActiveException() {

    super(NlsBundleTransaction.ERR_TRANSACTION_NOT_ACTIVE);
  }

}
