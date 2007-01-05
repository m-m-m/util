/* $Id$ */
package net.sf.mmm.search.indexer.api;

import java.io.Reader;

import net.sf.mmm.search.api.SearchEntry;

/**
 * This is the interface for an entry in the search index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableSearchEntry extends SearchEntry {

  /**
   * This enum contains the available modes for indexing and storing properties.
   * 
   * @see MutableSearchEntry#setProperty(String, String, Mode)
   */
  public enum Mode {

    /**
     * Indicates that the property will be tokenized and stored in the index.
     * This is the default.
     */
    TEXT,

    /**
     * Indicates that the property will NOT be tokenized so it can be searched
     * as it is. This is useful for technical properties such as
     * {@link #PROPERTY_UID} or {@link #PROPERTY_URI}.
     */
    NOT_TOKENIZED,

    /**
     * Indicates that the property will NOT be indexed but only stored. Such
     * property is hidden for the searcher but can be retrieved from the result.
     */
    NOT_INDEXED,

    /**
     * Indicates that the property will only be tokenized but NOT be stored in
     * the index. Entries can still be found as with {@link #TEXT} but the
     * property can NOT be {@link SearchEntry#getProperty(String) retrieved}
     * from the {@link net.sf.mmm.search.engine.api.SearchHit hit}. This option
     * allows to reduce the size of the search-index but also disables
     * {@link net.sf.mmm.search.engine.api.SearchHit#getHighlightedText() highlighting}
     * if used for {@link #PROPERTY_TEXT}.
     */
    NOT_STORED,
  }

  /**
   * This method sets the property with the given <code>name</code> to the
   * given <code>value</code>.
   * 
   * @param name
   *        is the name of the property. E.g. {@link #PROPERTY_TEXT "text"}.
   * @param value
   *        is the value of the property to set.
   * @param mode
   *        determines how the property is indexed and stored.
   */
  void setProperty(String name, String value, Mode mode);

  /**
   * This method sets the property with the given <code>name</code> to the
   * content of the given <code>reader</code>.<br>
   * Use this method if you want to set a property to a very long text with a
   * low memory consumption. The property mode will be {@link Mode#NOT_STORED}.
   * If this feature is NOT supported by the implementation, the content of the
   * reader is read as String into memory until a configureable maximum is
   * reached.<br>
   * <b>ATTENTION:</b><br>
   * The <code>valueReader</code> may be read only when the entry is
   * {@link SearchIndexer#add(MutableSearchEntry) written} to the index. When
   * you use this method please ensure that the reader stays open until the
   * entry is {@link SearchIndexer#add(MutableSearchEntry) written} and that you
   * {@link Reader#close() close} the reader if your did NOT write it.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param name
   *        is the name of the property. E.g. {@link #PROPERTY_TEXT "text"}.
   * @param valueReader
   *        is a reader to the value of the property to set.
   */
  void setProperty(String name, Reader valueReader);

  /**
   * This method sets the {@link #PROPERTY_URI URI}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param uri
   *        is the URI to set.
   */
  void setUri(String uri);

  /**
   * This method sets the {@link #PROPERTY_UID UID}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param uid
   *        is the UID to set.
   */
  void setUid(String uid);

  /**
   * This method sets the {@link #PROPERTY_TITLE title}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param title
   *        is the title to set.
   */
  void setTitle(String title);

  /**
   * This method sets the {@link #PROPERTY_TEXT text}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param text
   *        is the text to set.
   */
  void setText(String text);

  /**
   * This method sets the {@link #PROPERTY_KEYWORDS keywords}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param keywords
   *        are the keywords to set.
   */
  void setKeywords(String keywords);

  /**
   * This method sets the {@link #PROPERTY_AUTHOR author}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param author
   *        is the author to set.
   */
  void setAuthor(String author);

  /**
   * This method sets the {@link #PROPERTY_TYPE type}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param type
   *        is the type to set.
   */
  void setType(String type);

  /**
   * This method sets the {@link #PROPERTY_SIZE size}.
   * 
   * @see #setProperty(String, String, Mode)
   * 
   * @param size
   *        is the size to set.
   */
  void setSize(long size);

  /**
   * This method sets the boost of this this entry. The boost is a positive
   * value. The higher the boost, the
   * 
   * @param boost
   *        is the
   */
  void setBoost(double boost);

}
