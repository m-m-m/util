/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

/**
 * This is a default implementation of the
 * {@link net.sf.mmm.search.indexer.api.EntryUpdateVisitor} interface. It simply
 * does nothing but
 * {@link #getChangeCount(net.sf.mmm.util.event.api.ChangeType) counting}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CountingEntryUpdateVisitorDefault extends AbstractCountingEntryUpdateVisitor {

  /**
   * The constructor.
   */
  public CountingEntryUpdateVisitorDefault() {

    super();
  }

}
