/* $Id$ */
package net.sf.mmm.search.api;

import java.util.Iterator;

/**
 * This interface represents an entry of the search-index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchEntry {

  /**
   * The name of the "title" property. The title should always be present.
   */
  String PROPERTY_TITLE = "title";

  /**
   * The name of the "uri" property. The URI (Unified Resource Identifier)
   * should always be present and identify the content in a unique way. It can
   * be an URL or the essential part to create an URL used to link the content
   * when the search results are presented.<br>
   * If the content has a technical UID, the {@link #PROPERTY_UID} may be used
   * additionally.
   */
  String PROPERTY_URI = "uri";

  /**
   * The name of the "uid" property. The UID (Unique Idenitifier) is an optional
   * property that identifies the content technically unique. While the URI of a
   * content may change (e.g. if the content is renamed or moved) the UID stays
   * untouched and identifies the content for its complete lifetime.
   */
  String PROPERTY_UID = "uid";

  /**
   * The name of the "text" property. This is the default property to search on.
   * All textual information about the content should be stored in this
   * property.
   */
  String PROPERTY_TEXT = "text";

  /**
   * The name of the "keywords" property. This is an optional property that may
   * hold specific keywords (aka tags) that classify the content.
   */
  String PROPERTY_KEYWORDS = "keywords";

  /**
   * The name of the "size" property. This is an optional property that may hold
   * the size (aka content-length) of the content. This should be an integer
   * representing the size in bytes.
   */
  String PROPERTY_SIZE = "size";

  /**
   * The name of the "author" property. This is an optinal property that may
   * hold the author (aka artist) of the content.
   */
  String PROPERTY_AUTHOR = "author";

  /**
   * The name of the "type" property. This is an optinal property that
   * classifies the type (aka format or content-type) of the content. As values
   * typically eigther mime-types or file-extensions are used (but NOT mixed).
   */
  String PROPERTY_TYPE = "type";

  /**
   * The name of the "lang" property. This is an optinal property that specifies
   * the language of the content.
   */
  String PROPERTY_LANGUAGE = "lang";

  /**
   * This method is a generic accessor for additional properties of this
   * {@link SearchEntry entry}.
   * 
   * @param name
   *        is the name of the requested property.
   * @return the value of the requested property or <code>null</code> if no
   *         property exists for the given <code>name</code>.
   */
  String getProperty(String name);

  /**
   * This method gets the names of all {@link #getProperty(String) properties}
   * that are defined (NOT <code>null</code>).
   * 
   * @return a read-only iterator of the property names.
   */
  Iterator<String> getPropertyNames();

  /**
   * This method gets the URI that identifies the original content and allows to
   * link to it.
   * 
   * @see SearchEntry#PROPERTY_URI
   * 
   * @return the URI of this hit.
   */
  String getUri();

  /**
   * This method gets the UID that identifies the original content. While the
   * {@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() entry ID} is the
   * technical ID of the hit assigned by the underlying search-engine this is an
   * optional custom ID given by the user.
   * 
   * @see SearchEntry#PROPERTY_UID
   * 
   * @return the UID of this hit or <code>null</code> if NOT available.
   */
  String getUid();

  /**
   * This method gets the title used to display this hit.
   * 
   * @see SearchEntry#PROPERTY_TITLE
   * 
   * @return the title of this hit.
   */
  String getTitle();

  /**
   * This method gets the plain text of the content.
   * 
   * @see SearchEntry#PROPERTY_TEXT
   * 
   * @return the text of this hit.
   */
  String getText();

  /**
   * This method gets the type of the content.
   * 
   * @see SearchEntry#PROPERTY_TYPE
   * 
   * @return the type of this hit or <code>null</code> if NOT available.
   */
  String getType();

  /**
   * This method gets the size of the content.
   * 
   * @see SearchEntry#PROPERTY_SIZE
   * 
   * @return the size of this hit or <code>null</code> if NOT available.
   */
  String getSize();

}
