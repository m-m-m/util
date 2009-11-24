/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.Reader;
import java.util.Iterator;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import net.sf.mmm.search.impl.LuceneFieldNameIterator;
import net.sf.mmm.search.indexer.base.AbstractMutableSearchEntry;
import net.sf.mmm.util.nls.api.IllegalCaseException;

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
   * The constructor.
   * 
   * @param entry is the lucene document that represents the actual entry.
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
   * {@inheritDoc}
   */
  public void setBoost(double boost) {

    this.document.setBoost((float) boost);
  }

  /**
   * {@inheritDoc}
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
      default :
        throw new IllegalCaseException(Mode.class, mode);
    }
    Field field = new Field(name, value, store, index);
    this.document.add(field);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProperty(String name, Reader valueReader) {

    Field field = new Field(name, valueReader);
    this.document.add(field);
  }

  /**
   * {@inheritDoc}
   */
  public String getProperty(String name) {

    return this.document.get(name);
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<String> getPropertyNames() {

    return new LuceneFieldNameIterator(this.document);
  }

}
