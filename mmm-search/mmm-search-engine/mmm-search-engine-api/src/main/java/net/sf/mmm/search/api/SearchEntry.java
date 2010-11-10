/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api;

import java.util.Iterator;

/**
 * This interface represents an entry of the search-index. It is either used for
 * indexing (net.sf.mmm.search.indexer.api.SearchIndexer) or
 * {@link net.sf.mmm.search.engine.api.SearchEngine retrieval}.
 * 
 * @see net.sf.mmm.search.engine.api.SearchHit
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
   * identifies the original content and allows to link to it. Typically this is
   * not an entire URL but a path relative to the
   * {@link net.sf.mmm.search.api.config.SearchSource#getUrlPrefix URL-prefix}
   * of the according {@link #getSource() source}.<br>
   * <b>NOTE:</b><br>
   * This property should typically be present. Only if the content has a
   * technical UID, the {@link #PROPERTY_UID} can be used for linking the
   * content and the URI might NOT be set then. However it is best practice to
   * set the URI anyways (to the URL-suffix containing the UID for linking the
   * content).
   * 
   * @see #getUri()
   */
  String PROPERTY_URI = "uri";

  /**
   * The name of the "uid" property. The UID (Unique Identifier) is an optional
   * property that identifies the content uniquely. Unlike the
   * {@link net.sf.mmm.search.engine.api.SearchHit#getEntryId() entry ID} that
   * is the technical ID of the {@link SearchEntry} assigned by the underlying
   * search-engine, this is an optional custom ID given by the user of this API.
   * While the URI of a content may change (e.g. if the content is renamed or
   * moved) the UID has to stay untouched and identifies the content for its
   * complete lifetime.
   * 
   * 
   * @see #getUid()
   */
  String PROPERTY_UID = "uid";

  /**
   * The name of the "text" property. This is the default property to search on.
   * All textual information about the content should be stored in this
   * property.
   * 
   * @see #getText()
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
   * 
   * @see #getSize()
   */
  String PROPERTY_SIZE = "size";

  /**
   * The name of the "author" property. This is an optional property that may
   * hold the author (aka artist) of the content.
   * 
   * @see #getAuthor()
   */
  String PROPERTY_AUTHOR = "author";

  /**
   * The name of the "type" property. This is an optional property that
   * classifies the type (aka format or content-type) of the content. As values
   * typically either mime-types or file-extensions are used (but NOT mixed).
   * 
   * @see #getType()
   */
  String PROPERTY_TYPE = "type";

  /**
   * The name of the "lang" property. This is an optional property that
   * specifies the language of the content.
   */
  String PROPERTY_LANGUAGE = "lang";

  /**
   * The name of the "source" property. This property specifies the
   * {@link net.sf.mmm.search.api.config.SearchSource#getId() source} of the
   * content. It is not strictly required but should always be present in a
   * regular setup. If it is NOT present, the property {@link #PROPERTY_URI URI}
   * has to contain the complete URL to the content.
   * 
   * @see #getSource()
   * @see net.sf.mmm.search.api.config.SearchSource
   */
  String PROPERTY_SOURCE = "source";

  /**
   * This method is a generic accessor for additional properties of this
   * {@link SearchEntry entry}.
   * 
   * @param name is the name of the requested property.
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
   * This method gets the value of the {@link #PROPERTY_URI}.
   * 
   * @see #PROPERTY_URI
   * 
   * @return the URI of this entry.
   */
  String getUri();

  /**
   * This method gets the value of the {@link #PROPERTY_UID source property}.
   * 
   * @see #PROPERTY_UID
   * 
   * @return the UID of this entry or <code>null</code> if NOT available.
   */
  String getUid();

  /**
   * This method gets the value of the {@link #PROPERTY_TITLE title property}.
   * 
   * @see #PROPERTY_TITLE
   * 
   * @return the title of this entry.
   */
  String getTitle();

  /**
   * This method gets the author of this entry.
   * 
   * @see #PROPERTY_AUTHOR
   * 
   * @return the author of this entry or <code>null</code> if NOT available.
   */
  String getAuthor();

  /**
   * This method gets the value of the {@link #PROPERTY_SOURCE text property}.
   * 
   * @see #PROPERTY_TEXT
   * 
   * @return the text of this entry.
   */
  String getText();

  /**
   * This method gets the value of the {@link #PROPERTY_TYPE type property}.
   * 
   * @see #PROPERTY_TYPE
   * 
   * @return the type of this entry or <code>null</code> if NOT available.
   */
  String getType();

  /**
   * This method gets the value of the {@link #PROPERTY_SOURCE source property}.
   * 
   * @see #PROPERTY_SOURCE
   * 
   * @return the source of this entry or <code>null</code> if NOT available.
   */
  String getSource();

  /**
   * This method gets the size of the content.
   * 
   * @see #PROPERTY_SIZE
   * 
   * @return the size of this entry or <code>null</code> if NOT available.
   */
  Long getSize();

}
