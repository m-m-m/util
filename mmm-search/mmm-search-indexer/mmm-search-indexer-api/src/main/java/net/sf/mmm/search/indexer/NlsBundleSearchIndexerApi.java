/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for this subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleSearchIndexerApi extends AbstractResourceBundle {

  /**
   * @see net.sf.mmm.search.indexer.base.SearchUpdateMissingIdException
   */
  public static final String ERR_UPDATE_MISSING_ID = "Can not update entry: neither UID nor URI is set!";

  /**
   * @see net.sf.mmm.search.indexer.base.SearchRemoveFailedException
   */
  public static final String ERR_REMOVE_FAILED = "Failed to remove entry with "
      + "value \"{value}\" for property \"{property}\"!";

  /**
   * @see net.sf.mmm.search.indexer.base.SearchAddFailedException
   */
  public static final String ERR_ADD_FAILED = "Failed to add entry \"{0}\"!";

  /** See net.sf.mmm.search.indexer.base.AdvancedSearchIndexer. */
  public static final String MSG_MAIN_OPTION_USAGE_SPRING_XML = "The "
      + "optional XML based configuration used for the spring context given by "
      + "{operand}. By default annotation based config is used.";

  /** See net.sf.mmm.search.indexer.base.AdvancedSearchIndexer. */
  public static final String INT_INDEXER_MAIN_OPTION_NAME_SPRING_XML = "--spring-xml";

  /** See net.sf.mmm.search.indexer.base.AdvancedSearchIndexer. */
  public static final String MSG_MAIN_OPTION_USAGE_SPRING_PACKAGES = "The "
      + "optional list of java packages where spring should look for "
      + "annotated beans. The default is {default}. This option is ignored, if "
      + INT_INDEXER_MAIN_OPTION_NAME_SPRING_XML + " is set.";

  /** See net.sf.mmm.search.indexer.base.AdvancedSearchIndexer. */
  public static final String MSG_SEARCH_INDEXER_MAIN_MODE_USAGE_DEFAULT = "Perform "
      + "search-indexing according to the configuration.";

  /**
   * The constructor.
   */
  public NlsBundleSearchIndexerApi() {

    super();
  }

}
