/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.engine.api.SearchHit;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the abstract base implementation of the {@link SearchIndexer}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchIndexer implements SearchIndexer {

  /**
   * The constructor.
   */
  public AbstractSearchIndexer() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public int update(MutableSearchEntry entry) throws SearchException {

    int removeCount;
    String identifier = entry.getUid();
    if (identifier == null) {
      // attention: uri will only be unique in combination with source
      identifier = entry.getUri();
      if (identifier == null) {
        throw new SearchUpdateMissingIdException();
      }
      removeCount = removeByUri(identifier, entry.getSource());
    } else {
      removeCount = removeByUid(identifier);
    }
    add(entry);
    return removeCount;
  }

  /**
   * {@inheritDoc}
   */
  public boolean remove(SearchHit hit) throws SearchException {

    NlsNullPointerException.checkNotNull(SearchHit.class, hit);
    return removeById(hit.getEntryId());
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUid(String uid) throws SearchException {

    return remove(SearchEntry.PROPERTY_UID, uid);
  }

}
