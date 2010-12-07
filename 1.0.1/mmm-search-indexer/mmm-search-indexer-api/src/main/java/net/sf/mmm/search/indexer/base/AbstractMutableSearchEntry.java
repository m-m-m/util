/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.config.SearchFields;
import net.sf.mmm.search.base.BasicSearchEntry;
import net.sf.mmm.search.base.SearchDependencies;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;

/**
 * This is the abstract base implementation of the {@link MutableSearchEntry}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractMutableSearchEntry extends BasicSearchEntry implements
    MutableSearchEntry {

  /**
   * The constructor.
   * 
   * @param searchFields are the {@link SearchFields}.
   * @param searchDependencies are the {@link SearchDependencies}.
   */
  public AbstractMutableSearchEntry(SearchFields searchFields, SearchDependencies searchDependencies) {

    super(searchFields, searchDependencies);
  }

  /**
   * {@inheritDoc}
   */
  public void setCreator(String author) {

    setField(FIELD_CREATOR, author);
  }

  /**
   * {@inheritDoc}
   */
  public void setKeywords(String keywords) {

    setField(FIELD_KEYWORDS, keywords);
  }

  /**
   * {@inheritDoc}
   */
  public void setText(String text) {

    setField(FIELD_TEXT, text);
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    setField(FIELD_TITLE, title);
  }

  /**
   * {@inheritDoc}
   */
  public void setType(String type) {

    setField(FIELD_TYPE, type);
  }

  /**
   * {@inheritDoc}
   */
  public void setSource(String source) {

    setField(FIELD_SOURCE, source);
  }

  /**
   * {@inheritDoc}
   */
  public void setCustomId(Object cid) {

    setField(FIELD_CUSTOM_ID, cid);
  }

  /**
   * {@inheritDoc}
   */
  public void setUri(String uri) {

    setField(FIELD_URI, uri);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(long size) {

    setField(FIELD_SIZE, Long.valueOf(size));
  }

}
