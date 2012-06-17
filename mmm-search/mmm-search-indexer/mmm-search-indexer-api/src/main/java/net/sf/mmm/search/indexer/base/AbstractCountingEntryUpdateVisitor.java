/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.search.indexer.api.CountingEntryUpdateVisitor;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is the abstract base implementation of the
 * {@link CountingEntryUpdateVisitor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractCountingEntryUpdateVisitor implements CountingEntryUpdateVisitor {

  /** @see #getChangeCount(ChangeType) */
  private final Map<ChangeType, Counter> changeCountMap;

  /**
   * The constructor.
   */
  public AbstractCountingEntryUpdateVisitor() {

    super();
    this.changeCountMap = new HashMap<ChangeType, Counter>();
  }

  /**
   * {@inheritDoc}
   */
  public void visitIndexedEntryUri(String uri, ChangeType changeType) {

    Counter counter = this.changeCountMap.get(changeType);
    if (counter == null) {
      counter = new Counter();
      this.changeCountMap.put(changeType, counter);
    }
    counter.count++;
  }

  /**
   * {@inheritDoc}
   */
  public int getChangeCount(ChangeType changeType) {

    int result = 0;
    Counter counter = this.changeCountMap.get(changeType);
    if (counter != null) {
      result = counter.count;
    }
    return result;
  }

  /**
   * A simple counter (mutable {@link Integer} object).
   */
  private static final class Counter {

    /** The counter. */
    private int count;

    /**
     * The constructor.
     */
    private Counter() {

      super();
    }

  }

}
