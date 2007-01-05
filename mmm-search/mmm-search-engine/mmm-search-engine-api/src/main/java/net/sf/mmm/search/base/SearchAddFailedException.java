/* $Id$ */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsSearchEngineApiBundle;
import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} if the given entry could
 * NOT be added.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchAddFailedException extends SearchException {

  /** UID for serialization */
  private static final long serialVersionUID = -3642650920957925278L;

  /**
   * The constructor
   * 
   * @param entry
   *        is the entry that could NOT be added.
   */
  public SearchAddFailedException(SearchEntry entry) {

    super(NlsSearchEngineApiBundle.ERR_ADD_FAILED, "" + entry);
  }

  /**
   * The constructor
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param entry
   *        is the entry that could NOT be added.
   */
  public SearchAddFailedException(Throwable nested, SearchEntry entry) {

    super(nested, NlsSearchEngineApiBundle.ERR_ADD_FAILED, "" + entry);
  }

}
