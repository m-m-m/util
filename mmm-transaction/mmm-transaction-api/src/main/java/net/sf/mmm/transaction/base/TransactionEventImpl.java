/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.base;

import net.sf.mmm.transaction.api.TransactionContext;
import net.sf.mmm.transaction.api.TransactionEvent;
import net.sf.mmm.transaction.api.TransactionEventType;

/**
 * This is the implementation of the {@link TransactionEvent} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TransactionEventImpl implements TransactionEvent {

  /** @see #getType() */
  private final TransactionEventType type;

  /** @see #getContext() */
  private final TransactionContext context;

  /**
   * The constructor.
   * 
   * @param type is the {@link #getType() type}.
   * @param context is the {@link #getContext() context}.
   */
  public TransactionEventImpl(TransactionEventType type, TransactionContext context) {

    super();
    this.type = type;
    this.context = context;
  }

  /**
   * {@inheritDoc}
   */
  public TransactionContext getContext() {

    return this.context;
  }

  /**
   * {@inheritDoc}
   */
  public TransactionEventType getType() {

    return this.type;
  }

}
