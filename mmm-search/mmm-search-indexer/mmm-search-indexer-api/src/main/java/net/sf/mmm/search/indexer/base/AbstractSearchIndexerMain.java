/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.List;

import net.sf.mmm.search.api.config.SearchConfiguration;
import net.sf.mmm.search.indexer.NlsBundleSearchIndexerApi;
import net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer;
import net.sf.mmm.search.indexer.base.config.ConfiguredSearchIndexerOptionsBean;
import net.sf.mmm.util.cli.api.AbstractVersionedMain;
import net.sf.mmm.util.cli.api.CliMode;
import net.sf.mmm.util.cli.api.CliOption;
import net.sf.mmm.util.component.api.IocContainer;

/**
 * This is the abstract base-class for a fully integrated main program that triggers the
 * {@link net.sf.mmm.search.indexer.api.ConfiguredSearchIndexer search-indexer} according to a given
 * {@link net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration configuration}. It allows to do
 * complex indexing of files from arbitrary sources.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@CliMode(id = CliMode.ID_DEFAULT, usage = NlsBundleSearchIndexerApi.MSG_SEARCH_INDEXER_MAIN_MODE_USAGE_DEFAULT)
public abstract class AbstractSearchIndexerMain extends AbstractVersionedMain {

  /** The optional filename of the configuration XML-file. */
  @CliOption(name = "--config", aliases = "-c", required = false, operand = "FILE", //
  usage = "The XML-configuration file. The default is {default}.")
  private String configurationUrl;

  /** The option to overwrite the search-index and re-index from scratch. */
  @CliOption(name = "--overwrite", required = false, usage = "Overwrite the "
      + "search-index and reindex from scratch. If combined with --source, only "
      + "the entries of the specified source(s) are overwritten.")
  private boolean overwrite;

  /** The option to optimize the index at the end of indexing. */
  @CliOption(name = "--optimized", required = false, usage = "Optimize the "
      + "search-index after indexing. This will cause some performance overhead "
      + "after indexing but will typically speed up your searches. Default is {default}.")
  private Boolean optimize;

  /**
   * The option defining an optional {@link List} of source-IDs that should be updated in index rather than
   * all sources.
   */
  @CliOption(name = "--source", required = false, operand = "SOURCE", usage = "Only update the specified source-ID(s) in the index.")
  private List<String> sources;

  /**
   * The constructor.
   */
  public AbstractSearchIndexerMain() {

    super();
    this.configurationUrl = SearchConfiguration.DEFAULT_CONFIGURATION_URL;
    this.optimize = Boolean.TRUE;
  }

  /**
   * An {@link IocContainer} is required. Override this method to provide it.
   *
   * {@inheritDoc}
   */
  @Override
  protected abstract IocContainer getIocContainer();

  /**
   * {@inheritDoc}
   */
  @Override
  protected int runDefaultMode() {

    IocContainer container = getIocContainer();
    try {
      ConfiguredSearchIndexer configuredSearchIndexer = container.get(ConfiguredSearchIndexer.class);
      ConfiguredSearchIndexerOptionsBean options = new ConfiguredSearchIndexerOptionsBean();
      options.setOptimize(Boolean.TRUE.equals(this.optimize));
      options.setOverwriteEntries(this.overwrite);
      options.setSourceIds(this.sources);
      configuredSearchIndexer.index(this.configurationUrl, options);
    } finally {
      container.dispose();
    }
    return EXIT_CODE_OK;
  }
}
