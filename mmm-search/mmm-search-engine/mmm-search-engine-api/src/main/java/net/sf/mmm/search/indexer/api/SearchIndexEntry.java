/* $Id$ */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.api.SearchEntry;

/**
 * This is the interface for an entry in the search index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchIndexEntry extends SearchEntry {

  /**
   * This method sets the property with the given <code>name</code> to the
   * given <code>value</code>.
   * 
   * @param name
   *        is the name of the property. E.g. {@link #PROPERTY_TEXT "text"}.
   * @param value
   *        is the value of the property to set.
   * @param searchable
   *        if <code>true</code> the property will be searchable.
   * @param stored
   *        if <code>true</code> the value of the property will be stored in
   *        the index and can be retrieved.
   */
  void setProperty(String name, String value, boolean searchable, boolean stored);

  /**
   * This method sets the {@link #PROPERTY_URI URI}.
   * 
   * @see #setProperty(String, String, boolean, boolean)
   * 
   * @param uri
   *        is the URI to set.
   */
  void setUri(String uri);

  /**
   * This method sets the {@link #PROPERTY_UID UID}.
   * 
   * @see #setProperty(String, String, boolean, boolean)
   * 
   * @param uid
   *        is the UID to set.
   */
  void setUid(String uid);

  /**
   * This method sets the {@link #PROPERTY_TITLE title}.
   * 
   * @see #setProperty(String, String, boolean, boolean)
   * 
   * @param title
   *        is the title to set.
   */
  void setTitle(String title);

  /**
   * This method sets the {@link #PROPERTY_TEXT text}.
   * 
   * @see #setProperty(String, String, boolean, boolean)
   * 
   * @param text
   *        is the text to set.
   */
  void setText(String text);

  /**
   * This method sets the {@link #PROPERTY_KEYWORDS keywords}.
   * 
   * @see #setProperty(String, String, boolean, boolean)
   * 
   * @param keywords
   *        are the keywords to set.
   */
  void setKeywords(String keywords);

  /**
   * This method sets the {@link #PROPERTY_AUTHOR author}.
   * 
   * @see #setProperty(String, String, boolean, boolean)
   * 
   * @param author
   *        is the author to set.
   */
  void setAuthor(String author);

  /**
   * This method sets the boost of this this entry. The boost is a positive
   * value. The higher the boost, the 
   * 
   * @param boost
   *        is the
   */
  void setBoost(double boost);

}
