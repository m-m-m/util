/* $Id$ */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsSearchEngineApiBundle;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer#update(net.sf.mmm.search.indexer.api.MutableSearchEntry)}
 * if the given entry has no ID set.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchUpdateMissingIdException extends SearchException {

  /** UID for serialization */
  private static final long serialVersionUID = 1458156115842499131L;

  /**
   * The constructor
   */
  public SearchUpdateMissingIdException() {

    super(NlsSearchEngineApiBundle.ERR_UPDATE_MISSING_ID);
  }

}
