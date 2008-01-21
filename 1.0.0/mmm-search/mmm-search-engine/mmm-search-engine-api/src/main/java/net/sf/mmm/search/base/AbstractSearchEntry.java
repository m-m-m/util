/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.search.api.SearchEntry;

/**
 * This is the abstract base implementation of the {@link SearchEntry}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchEntry implements SearchEntry {

  /**
   * The constructor.
   */
  public AbstractSearchEntry() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return getProperty(PROPERTY_TITLE);
  }

  /**
   * {@inheritDoc}
   */
  public String getUri() {

    return getProperty(PROPERTY_URI);
  }

  /**
   * {@inheritDoc}
   */
  public String getUid() {

    return getProperty(PROPERTY_UID);
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return getProperty(PROPERTY_TEXT);
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return getProperty(PROPERTY_TYPE);
  }

  /**
   * {@inheritDoc}
   */
  public String getAuthor() {

    return getProperty(PROPERTY_AUTHOR);
  }

  /**
   * {@inheritDoc}
   */
  public String getSource() {

    return getProperty(PROPERTY_SOURCE);
  }

  /**
   * {@inheritDoc}
   */
  public Long getSize() {

    Long size = null;
    String sizeString = getProperty(PROPERTY_SIZE);
    if (sizeString != null) {
      try {
        size = Long.valueOf(sizeString);
      } catch (NumberFormatException e) {
        // ignore...
      }
    }
    return size;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String id = getUid();
    if (id == null) {
      id = getUri();
      if (id == null) {
        id = getTitle();
        if (id == null) {
          id = "undefined";
        }
      }
    }
    return id;
  }

}
