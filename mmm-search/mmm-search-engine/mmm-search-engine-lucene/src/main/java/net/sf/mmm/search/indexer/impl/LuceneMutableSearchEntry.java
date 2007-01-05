/* $Id$ */
package net.sf.mmm.search.indexer.impl;

import java.io.Reader;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import net.sf.mmm.search.impl.LuceneFieldNameIterator;
import net.sf.mmm.search.indexer.base.AbstractMutableSearchEntry;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.MutableSearchEntry} interface using
 * lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneMutableSearchEntry extends AbstractMutableSearchEntry {

  /** the lucene document that represents the actual entry */
  private final Document document;

  /**
   * The constructor
   * 
   * @param entry
   *        is the lucene document that represents the actual entry.
   */
  public LuceneMutableSearchEntry(Document entry) {

    super();
    this.document = entry;
  }

  /**
   * This method gets the unwrapped lucene document.
   * 
   * @return the lucene document.
   */
  public Document getLuceneDocument() {

    return this.document;
  }

  /**
   * @see net.sf.mmm.search.indexer.api.MutableSearchEntry#setBoost(double)
   */
  public void setBoost(double boost) {

    this.document.setBoost((float) boost);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.MutableSearchEntry#setProperty(java.lang.String,
   *      java.lang.String, Mode)
   */
  public void setProperty(String name, String value, Mode mode) {

    Field.Store store = Field.Store.YES;
    Field.Index index = Field.Index.TOKENIZED;
    switch (mode) {
      case TEXT:
        // default
        break;
      case NOT_INDEXED:
        index = Field.Index.NO;
        break;
      case NOT_STORED:
        store = Field.Store.NO;
        break;
      case NOT_TOKENIZED:
        index = Field.Index.UN_TOKENIZED;
        break;
    }
    Field field = new Field(name, value, store, index);
    this.document.add(field);
  }

  /**
   * @see net.sf.mmm.search.indexer.api.MutableSearchEntry#setProperty(java.lang.String,
   *      java.io.Reader)
   */
  @Override
  public void setProperty(String name, Reader valueReader) {

    Field field = new Field(name, valueReader);
    this.document.add(field);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getProperty(java.lang.String)
   */
  public String getProperty(String name) {

    return this.document.get(name);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getPropertyNames()
   */
  public Iterator<String> getPropertyNames() {

    return new LuceneFieldNameIterator(this.document);
  }

}
