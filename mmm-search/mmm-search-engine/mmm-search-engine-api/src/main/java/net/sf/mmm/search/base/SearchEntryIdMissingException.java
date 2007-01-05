/* $Id$ */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsSearchEngineApiBundle;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from
 * {@link net.sf.mmm.search.engine.api.SearchEngine#getEntry(String)} if the
 * given {@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() entry ID}
 * does NOT exist in the index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchEntryIdMissingException extends SearchException {

  /** UID for serialization */
  private static final long serialVersionUID = 301902932006670136L;

  /**
   * The constructor
   * 
   * @param entryId
   *        is the invalid entry ID.
   */
  public SearchEntryIdMissingException(String entryId) {

    super(NlsSearchEngineApiBundle.ERR_ENTRY_ID_MISSING, entryId);
  }

  /**
   * The constructor
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param entryId
   *        is the invalid entry ID.
   */
  public SearchEntryIdMissingException(Throwable nested, String entryId) {

    super(nested, NlsSearchEngineApiBundle.ERR_ENTRY_ID_MISSING, entryId);
  }

}
