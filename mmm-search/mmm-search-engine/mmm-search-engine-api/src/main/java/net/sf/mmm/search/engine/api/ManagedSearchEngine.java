/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api;

/**
 * This is the interface for a managed {@link SearchEngine}. It extends
 * {@link SearchEngine} with additional management methods.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ManagedSearchEngine extends SearchEngine {

  /**
   * This method tells the search-engine to refresh explicitly. Depending on the
   * implementation this may cause that the search index is reread from the disk
   * or that caches are purged.<br>
   * This can be an expensive operation and should only be called when the index
   * has been updated (and NOT per search request).
   */
  void refresh();

  /**
   * This method disposes the search-engine. This method needs to be called when
   * the search-engine is NOT used anymore. It will free used resources (e.g.
   * close open streams, etc.). After the call of this method this search-engine
   * AND all results retrieved from this search-engine can NOT be used anymore.
   */
  void dispose();

}
