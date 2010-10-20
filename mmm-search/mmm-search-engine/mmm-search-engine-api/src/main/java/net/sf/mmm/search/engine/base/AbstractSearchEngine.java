/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the abstract base implementation of the {@link ManagedSearchEngine}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchEngine extends AbstractLoggable implements ManagedSearchEngine {

  /** The {@link SearchEngineRefresher}. */
  private final SearchEngineRefresher searchEngineRefresher;

  /**
   * The constructor.
   * 
   * @param searchEngineRefresher is the {@link SearchEngineRefresher}.
   */
  public AbstractSearchEngine(SearchEngineRefresher searchEngineRefresher) {

    super();
    this.searchEngineRefresher = searchEngineRefresher;
    initialize();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doInitialize() {

    super.doInitialize();
  }

  /**
   * {@inheritDoc}
   */
  public void close() {

    if (this.searchEngineRefresher != null) {
      this.searchEngineRefresher.removeSearchEngine(this);
    }
  }

}
