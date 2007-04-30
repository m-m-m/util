/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import java.io.Reader;

import net.sf.mmm.search.base.AbstractSearchEntry;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;

/**
 * This is the abstract base implementation of the {@link MutableSearchEntry}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractMutableSearchEntry extends AbstractSearchEntry implements
    MutableSearchEntry {

  /**
   * The constructor.
   */
  public AbstractMutableSearchEntry() {

    super();
  }

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation of this feature used as fallback. Please
   * override this method if the underlying search-engine supports it.
   */
  public void setProperty(String name, Reader valueReader) {

  }

  /**
   * {@inheritDoc}
   */
  public void setAuthor(String author) {

    setProperty(PROPERTY_AUTHOR, author, Mode.TEXT);
  }

  /**
   * {@inheritDoc}
   */
  public void setKeywords(String keywords) {

    setProperty(PROPERTY_KEYWORDS, keywords, Mode.TEXT);
  }

  /**
   * {@inheritDoc}
   */
  public void setText(String text) {

    setProperty(PROPERTY_TEXT, text, Mode.TEXT);
  }

  /**
   * {@inheritDoc}
   */
  public void setTitle(String title) {

    setProperty(PROPERTY_TITLE, title, Mode.TEXT);
  }

  /**
   * {@inheritDoc}
   */
  public void setType(String type) {

    setProperty(PROPERTY_TYPE, type, Mode.NOT_TOKENIZED);
  }

  /**
   * {@inheritDoc}
   */
  public void setSource(String source) {

    setProperty(PROPERTY_SOURCE, source, Mode.NOT_TOKENIZED);
  }

  /**
   * {@inheritDoc}
   */
  public void setUid(String uid) {

    setProperty(PROPERTY_UID, uid, Mode.NOT_TOKENIZED);
  }

  /**
   * {@inheritDoc}
   */
  public void setUri(String uri) {

    setProperty(PROPERTY_URI, uri, Mode.NOT_TOKENIZED);
  }

  /**
   * {@inheritDoc}
   */
  public void setSize(long size) {

    setProperty(PROPERTY_SIZE, Long.toString(size), Mode.NOT_TOKENIZED);
  }

}
