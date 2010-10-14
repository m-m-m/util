/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;

/**
 * This is the interface for a component that periodically
 * {@link ManagedSearchEngine#refresh() refreshes}
 * {@link #addSearchEngine(ManagedSearchEngine) registered}
 * {@link ManagedSearchEngine search-engines} during the time from
 * {@link #startup() initialization} to {@link #shutdown() disposal}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEngineRefresher {

  /**
   * This method will initialize and startup this refresher. This will typically
   * start a new thread that periodically performs a refresh.<br/>
   * Multiple calls of this method should have no further effect. There is no
   * order specified between calls of this method and
   * {@link #addSearchEngine(ManagedSearchEngine)}.<br/>
   * <b>NOTE:</b><br>
   * This is intentionally NOT performed automatically via
   * {@link net.sf.mmm.util.component.base.AbstractComponent#initialize()} so
   * the startup only happens if explicitly required and not accidently because
   * this component if found and managed by some container.
   */
  void startup();

  /**
   * This method will register the given <code>searchEngine</code> so it will be
   * {@link ManagedSearchEngine#refresh() refreshed} periodically.<br/>
   * Implementations of this method have to be synchronized so new
   * {@link ManagedSearchEngine search-engines} can be registered after
   * {@link #startup() initialization}.<br/>
   * <b>ATTENTION:</b><br>
   * If this method is called after {@link #shutdown()}, it will cause an
   * unspecified {@link RuntimeException}.
   * 
   * @param searchEngine is the {@link ManagedSearchEngine search-engine} to
   *        register.
   */
  void addSearchEngine(ManagedSearchEngine searchEngine);

  /**
   * This method removes the given <code>searchEngine</code> from this
   * refresher. This will typically happen when the {@link ManagedSearchEngine
   * search-engine} is {@link ManagedSearchEngine#dispose() disposed}.<br/>
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
  void shutdown();

}
