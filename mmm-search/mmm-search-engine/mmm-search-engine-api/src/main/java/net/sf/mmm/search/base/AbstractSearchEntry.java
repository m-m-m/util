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
   * @see net.sf.mmm.search.api.SearchEntry#getAuthor()
   */
  public String getAuthor() {
  
    return getProperty(PROPERTY_AUTHOR);
  }
  
  /**
   * @see net.sf.mmm.search.api.SearchEntry#getSize()
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
   * @see java.lang.Object#toString()
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
