/* $Id$ */
package net.sf.mmm.search.indexer.impl;

import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import net.sf.mmm.search.indexer.base.AbstractSearchIndexEntry;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexEntry} interface using lucene
 * as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchIndexEntry extends AbstractSearchIndexEntry {

  /** the lucene document that represents the actual entry */
  private final Document document;

  /**
   * The constructor
   * 
   * @param entry
   *        is the lucene document that represents the actual entry.
   */
  public LuceneSearchIndexEntry(Document entry) {

    super();
    this.document = entry;
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setBoost(double)
   */
  public void setBoost(double boost) {

    this.document.setBoost((float) boost);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexEntry#setProperty(java.lang.String,
   *      java.lang.String, boolean, boolean)
   */
  public void setProperty(String name, String value, boolean searchable, boolean stored) {

    Field.Store store;
    if (stored) {
      store = Field.Store.YES;
    } else {
      store = Field.Store.NO;      
    }
    Field.Index index;
    if (searchable) {
      index = Field.Index.TOKENIZED;
    } else {
      index = Field.Index.NO;      
    }
    Field field = new Field(name, value, store, index);
    this.document.add(field);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getProperty(java.lang.String)
   */
  public String getProperty(String name) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getPropertyNames()
   */
  public Iterator<String> getPropertyNames() {

    // TODO Auto-generated method stub
    return null;
  }

}
