/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.indexer.api.EntryUpdateVisitor;
import net.sf.mmm.util.event.api.ChangeType;

/**
 * This is a dummy implementation of the {@link EntryUpdateVisitor} interface.
 * It simply does nothing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EntryUpdateVisitorDummy implements EntryUpdateVisitor {

  /** The singleton instance. */
  public static final EntryUpdateVisitorDummy INSTANCE = new EntryUpdateVisitorDummy();

  /**
   * The constructor.
   */
  public EntryUpdateVisitorDummy() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void visitIndexedEntryUri(String uri, ChangeType changeType) {

    // dummy - nothing to do
  }

}
