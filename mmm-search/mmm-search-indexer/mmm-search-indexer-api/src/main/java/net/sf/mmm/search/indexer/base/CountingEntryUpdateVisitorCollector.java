/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.search.indexer.api.EntryUpdateVisitor} interface that collects
 * all {@link #visitIndexedEntryUri(String, ChangeType) visited} URIs except for
 * {@link ChangeType#REMOVE}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CountingEntryUpdateVisitorCollector extends AbstractCountingEntryUpdateVisitor {

  /** @see #getEntryUriSet() */
  private final Set<String> entryUriSet;

  /**
   * The constructor.
   */
  public CountingEntryUpdateVisitorCollector() {

    super();
    this.entryUriSet = new HashSet<String>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void visitIndexedEntryUri(String uri, ChangeType changeType) {

    super.visitIndexedEntryUri(uri, changeType);
    if (changeType != ChangeType.REMOVE) {
      this.entryUriSet.add(uri);
    }
  }

  /**
   * This method gets the {@link Set} with the collected URIs.
   * 
   * @return the {@link Set} of URIs.
   */
  public Set<String> getEntryUriSet() {

    return this.entryUriSet;
  }

}
