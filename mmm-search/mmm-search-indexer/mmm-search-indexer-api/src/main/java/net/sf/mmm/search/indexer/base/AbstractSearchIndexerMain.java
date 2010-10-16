/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer;
import net.sf.mmm.util.cli.api.AbstractVersionedMain;
import net.sf.mmm.util.cli.api.CliModeObject;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.component.api.IocContainer;

/**
 * This is the abstract base-class for a fully integrated main program that
 * triggers the {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer
 * search-indexer} according to a given
 * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration
 * configuration}. It allows to do complex indexing of files from arbitrary
 * sources.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchIndexerMain extends AbstractVersionedMain {

  /** The optional filename of the configuration XML-file. */
  @CliOption(name = "--config", aliases = "-c", required = false, operand = "FILE", usage = "The XML-configuration file. The default is {default}.")
  private String configurationUrl;

  /**
   * The constructor.
   */
  public AbstractSearchIndexerMain() {

    super();
    this.configurationUrl = SearchConfiguration.DEFAULT_CONFIGURATION_URL;
  }

  /**
   * This method gets the {@link IocContainer} used to manage components with
   * their implementation. The {@link IocContainer} will be created and
   * initialized on the first call of this method.
   * 
   * @return the {@link IocContainer}.
   */
  protected abstract IocContainer getIocContainer();

  /**
   * {@inheritDoc}
   */
  @Override
  protected int run(CliModeObject mode) throws Exception {

    IocContainer container = getIocContainer();
    try {
      ConfiguredSearchIndexer configuredSearchIndexer = container
          .getComponent(ConfiguredSearchIndexer.class);
      configuredSearchIndexer.index(this.configurationUrl);
    } finally {
      container.dispose();
    }
    return 0;
  }

}
