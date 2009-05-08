/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleTransaction extends AbstractResourceBundle {

  /** @see net.sf.mmm.transaction.base.TransactionNotActiveException */
  public static final String ERR_TRANSACTION_NOT_ACTIVE = "The active transaction has already been closed. You can NOT commit or rollback at this state!";

  /** @see net.sf.mmm.transaction.base.TransactionCommitException */
  public static final String ERR_TRANSACTION_COMMIT = "The transaction could NOT be committed!";

  /** @see net.sf.mmm.transaction.base.TransactionRollbackException */
  public static final String ERR_TRANSACTION_ROLLBACK = "The transaction could NOT be rolled back!";

}
