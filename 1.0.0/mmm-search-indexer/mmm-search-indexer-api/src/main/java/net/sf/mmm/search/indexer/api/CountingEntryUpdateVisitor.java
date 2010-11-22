/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.util.event.api.ChangeType;

/**
 * This interface extends {@link EntryUpdateVisitor} with a little statistic of
 * the {@link #getChangeCount(ChangeType) counts of the different changes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CountingEntryUpdateVisitor extends EntryUpdateVisitor {

  /**
   * This method gets the number of invocations of
   * {@link EntryUpdateVisitor#visitIndexedEntryUri(String, ChangeType)} for the
   * given <code>changeType</code>.
   * 
   * @param changeType is the {@link ChangeType} or <code>null</code> for
   *        unmodified.
   * @return the number of according changes.
   */
  int getChangeCount(ChangeType changeType);

}
