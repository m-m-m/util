/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.impl;

import java.util.Iterator;

import org.apache.lucene.document.Document;

import net.sf.mmm.search.base.AbstractSearchEntry;

/**
 * This is the implementation of the {@link net.sf.mmm.search.api.SearchEntry}
 * interface using lucene as underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchEntry extends AbstractSearchEntry {

  /** the lucene document that represents the actual entry */
  private final Document document;

  /**
   * The constructor.
   * 
   * @param entry is the lucene document that represents the actual entry.
   */
  public LuceneSearchEntry(Document entry) {

    super();
    this.document = entry;
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
