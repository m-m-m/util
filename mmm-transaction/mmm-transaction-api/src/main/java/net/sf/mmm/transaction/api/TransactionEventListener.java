/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.api;

import net.sf.mmm.util.event.api.EventListener;

/**
 * This is the interface for a {@link EventListener listener} of
 * {@link TransactionEvent}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TransactionEventListener extends EventListener<TransactionEvent> {

  // nothing to add, just bound generic

}
