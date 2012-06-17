/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.indexer.base.AbstractConfiguredSearchIndexer;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer}.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
@Singleton
public class ConfiguredSearchIndexerImpl extends AbstractConfiguredSearchIndexer {

  /**
   * The constructor.
   */
  public ConfiguredSearchIndexerImpl() {

    super();
  }

  /**
   * This method initializes this object.
   */
  @Override
  public void doInitialize() {

    super.doInitialize();
  }

}
