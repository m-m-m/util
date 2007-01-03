/* $Id$ */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.base.AbstractSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexEntry;

/**
 * This is the abstract base implementation of the {@link SearchIndexEntry}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchIndexEntry extends AbstractSearchEntry implements
    SearchIndexEntry {

  /**
   * The constructor
   */
  public AbstractSearchIndexEntry() {

    super();
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setAuthor(java.lang.String)
   */
  public void setAuthor(String author) {

    setProperty(PROPERTY_AUTHOR, author, true, true);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setKeywords(java.lang.String)
   */
  public void setKeywords(String keywords) {

    setProperty(PROPERTY_KEYWORDS, keywords, true, true);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setText(java.lang.String)
   */
  public void setText(String text) {

    setProperty(PROPERTY_TEXT, text, true, true);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setTitle(java.lang.String)
   */
  public void setTitle(String title) {

    setProperty(PROPERTY_TITLE, title, true, true);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setUid(java.lang.String)
   */
  public void setUid(String uid) {

    setProperty(PROPERTY_UID, uid, true, true);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setUri(java.lang.String)
   */
  public void setUri(String uri) {

    setProperty(PROPERTY_URI, uri, true, true);
  }

}
