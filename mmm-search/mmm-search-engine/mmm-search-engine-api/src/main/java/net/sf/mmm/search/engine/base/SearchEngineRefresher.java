/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.io.Closeable;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;

/**
 * This is the interface for a component that periodically
 * {@link ManagedSearchEngine#refresh() refreshes}
 * {@link #addSearchEngine(ManagedSearchEngine) registered}
 * {@link ManagedSearchEngine search-engines}. The refresh of a
 * {@link ManagedSearchEngine search-engine} will happen until it is
 * {@link #removeSearchEngine(ManagedSearchEngine) de-registered} or this
 * refresher is {@link #close() closed}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineRefresher extends Closeable {

  /**
   * This method will register the given <code>searchEngine</code> so it will be
   * {@link ManagedSearchEngine#refresh() refreshed} periodically.<br/>
   * A common implementation will use a central thread that is started if the
   * first {@link ManagedSearchEngine search-engine} is added here.<br/>
   * <b>ATTENTION:</b><br>
   * If this method is called after {@link #close()}, it will cause an
   * unspecified {@link RuntimeException}.
   * 
   * @param searchEngine is the {@link ManagedSearchEngine search-engine} to
   *        register.
   */
  void addSearchEngine(ManagedSearchEngine searchEngine);

  /**
   * This method removes the given <code>searchEngine</code> from this
   * refresher. This will typically happen when the {@link ManagedSearchEngine
   * search-engine} is {@link ManagedSearchEngine#close() disposed}.<br/>
   * If the given <code>searchEngine</code> has never been
   * {@link #addSearchEngine(ManagedSearchEngine) registered}, this method has
   * no effect.
   * 
   * @param searchEngine is the {@link ManagedSearchEngine search-engine} to
   *        de-register.
   */
  void removeSearchEngine(ManagedSearchEngine searchEngine);

  /**
   * This method will shutdown and destroy this refresher.
   */
  void close();

}
