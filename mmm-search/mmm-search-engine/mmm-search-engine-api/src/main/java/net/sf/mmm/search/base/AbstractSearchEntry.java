/* $Id$ */
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
   * The constructor
   */
  public AbstractSearchEntry() {

    super();
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getTitle()
   */
  public String getTitle() {

    return getProperty(PROPERTY_TITLE);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getUri()
   */
  public String getUri() {

    return getProperty(PROPERTY_URI);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getUid()
   */
  public String getUid() {

    return getProperty(PROPERTY_UID);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getText()
   */
  public String getText() {

    return getProperty(PROPERTY_TEXT);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getType()
   */
  public String getType() {

    return getProperty(PROPERTY_TYPE);
  }

  /**
   * @see net.sf.mmm.search.api.SearchEntry#getSize()
   */
  public String getSize() {

    return getProperty(PROPERTY_SIZE);
  }

}
