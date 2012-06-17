/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.util.Collections;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.indexer.NlsBundleSearchIndexerApi;

/**
 * This is the exception thrown from
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer#update(net.sf.mmm.search.indexer.api.MutableSearchEntry)}
 * if the given entry has no ID set.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchUpdateMissingIdException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1458156115842499131L;

  /**
   * The constructor.
   */
  public SearchUpdateMissingIdException() {

    super(NlsBundleSearchIndexerApi.ERR_UPDATE_MISSING_ID, Collections.<String, Object> emptyMap());
  }

}
