/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.transaction.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.transaction.api.TransactionContext;

/**
 * This is the implementation of the {@link TransactionContext} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TransactionContextImpl implements TransactionContext {

  /** @see #getMap() */
  private Map<String, Object> map;

  /**
   * The constructor.
   */
  public TransactionContextImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Object> getMap() {

    if (this.map == null) {
      // does NOT need to be thread-safe since a single transactions is NOT to
      // be accessed concurrently from different threads.
      this.map = new HashMap<String, Object>();
    }
    return this.map;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEmpty() {

    if (this.map == null) {
      return true;
    }
    return this.map.isEmpty();
  }

}
