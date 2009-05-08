/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

import net.sf.mmm.util.event.api.Event;

/**
 * This is the {@link Event} send if a transaction has been
 * {@link TransactionAdapter#commit() committed} or
 * {@link TransactionAdapter#rollback() rolled back}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransactionEvent extends Event {

  /**
   * This method gets the type of this event.
   * 
   * @return the event-type.
   */
  TransactionEventType getType();

  /**
   * This method gets the {@link TransactionContext} associated with the
   * transaction that caused this event.
   * 
   * @return the {@link TransactionContext}.
   */
  TransactionContext getContext();

}
