/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.api;

import java.util.Iterator;

import net.sf.mmm.util.lang.api.attribute.AttributeReadId;

/**
 * This interface represents an entry of the search-index. A {@link SearchEntry} represents a single content
 * (file, web-page, etc.) and consists of {@link #getFieldAsString(String) fields} with the according
 * metadata. It is either used for indexing (net.sf.mmm.search.indexer.api.SearchIndexer) or
 * {@link net.sf.mmm.search.engine.api.SearchEngine retrieval}.
 * 
 * @see net.sf.mmm.search.api.config.SearchFields
 * @see net.sf.mmm.search.engine.api.SearchHit
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchEntry extends AttributeReadId<Long> {

  /**
   * The name of the "id" field. The UID (Unique Identifier) is a field that identifies the content uniquely. <br>
   * It is strongly suggested for the implementation but NOT strictly guaranteed that the ID remains the same
   * if a {@link SearchEntry} is updated in the index. The ID is a {@link Long} value. End-users should never
   * make any assumptions or interpretation of its value as this is totally implementation specific.
   * 
   * @see #getId()
   */
  String FIELD_ID = "id";

  /**
   * The name of the "title" field. The title should always be present.
   */
  String FIELD_TITLE = "title";

  /**
   * The name of the {@value} field. The URI (Unified Resource Identifier) identifies the original content and
   * allows to link to it. Typically this is not an entire URL but a path relative to the
   * {@link net.sf.mmm.search.api.config.SearchSource#getUrlPrefix URL-prefix} of the according
   * {@link #getSource() source}. <br>
   * <b>NOTE:</b><br>
   * This field should typically be present. Only if the content has a technical UID, the
   * {@link #FIELD_CUSTOM_ID} can be used for linking the content and the URI might NOT be set then. However
   * it is best practice to set the URI anyways (to the URL-suffix containing the UID for linking the
   * content).
   * 
   * @see #getUri()
   */
  String FIELD_URI = "uri";

  /**
   * The name of the {@value} field. The custom Identifier (CID) is an optional field that identifies the
   * content uniquely. Unlike the {@link #getId() ID} this is an optional custom ID given by the user of this
   * API. It can be in any arbitrary format. While the URI of a content may change (e.g. if the content is
   * renamed or moved) the UID has to stay untouched and identifies the content for its complete lifetime.
   * 
   * @see #getCustomId()
   */
  String FIELD_CUSTOM_ID = "cid";

  /**
   * The name of the {@value} field. This is the default field to search on. All textual information about the
   * content should be stored in this field.
   * 
   * @see #getText()
   */
  String FIELD_TEXT = "text";

  /**
   * The name of the {@value} field. This is an optional field that may hold specific keywords (also called
   * tags) that classify the content.
   */
  String FIELD_KEYWORDS = "keywords";

  /**
   * The name of the {@value} field. This is an optional field that may hold the size (also called
   * content-length) of the content. This should be a {@link Long} value representing the size in bytes.
   * 
   * @see #getSize()
   */
  String FIELD_SIZE = "size";

  /**
   * The name of the {@value} field. This is an optional field that may hold the author (aka artist) of the
   * content.
   * 
   * @see #getCreator()
   */
  String FIELD_CREATOR = "creator";

  /**
   * The name of the "type" field. This is an optional field that classifies the type (aka format or
   * content-type) of the content. As values typically either mime-types or file-extensions are used (but NOT
   * mixed).
   * 
   * @see #getType()
   */
  String FIELD_TYPE = "type";

  /**
   * The name of the "lang" field. This is an optional field that specifies the language of the content.
   */
  String FIELD_LANGUAGE = "lang";

  /**
   * The name of the "source" field. This field specifies the
   * {@link net.sf.mmm.search.api.config.SearchSource#getId() source} of the content. It is not strictly
   * required but should always be present in a regular setup. If it is NOT present, the field
   * {@link #FIELD_URI URI} has to contain the complete URL to the content.
   * 
   * @see #getSource()
   * @see net.sf.mmm.search.api.config.SearchSource
   */
  String FIELD_SOURCE = "source";

  /**
   * This method is a generic accessor for fields of this {@link SearchEntry entry}. For the predefine fields
   * (see <code>FIELD_*</code> constants like {@link #FIELD_TEXT}) there are specific getter methods like
   * {@link #getText()}.
   * 
   * @see #getField(String, Class)
   * 
   * @param name is the {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getName() name} of the
   *        requested field.
   * @return the value of the field in its original
   *         {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getType() type}.
   */
  Object getField(String name);

  /**
   * This method is a shortcut for <code>{@link #getField(String, Class) getField}(name, String.class)</code>.
   * 
   * @param name is the {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getName() name} of the
   *        requested field.
   * @return the value of the requested field or <code>null</code> if no field exists for the given
   *         <code>name</code>.
   */
  String getFieldAsString(String name);

  /**
   * This method is a generic accessor for fields of this {@link SearchEntry entry} with a specific
   * <code>type</code>. <br>
   * The type {@link String} will always work. Other types have to be compatible to the
   * {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getType() field type} (or a
   * {@link RuntimeException} will be thrown).
   * 
   * @param <T> is the generic type expected for the requested field-value (see parameter <code>type</code>).
   * 
   * @param name is the {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getName() name} of the
   *        requested field.
   * @param type is the type of the requested field.
   * @return the value of the requested field or <code>null</code> if no field exists for the given
   *         <code>name</code>.
   */
  <T> T getField(String name, Class<T> type);

  /**
   * This method gets the names of all {@link #getFieldAsString(String) fields} that are defined (NOT
   * <code>null</code>).
   * 
   * @return a read-only iterator of the field names.
   */
  Iterator<String> getFieldNames();

  /**
   * This method gets the value of the {@link #FIELD_URI}.
   * 
   * @see #FIELD_URI
   * 
   * @return the URI of this entry.
   */
  String getUri();

  /**
   * This method gets the value of the {@link #FIELD_CUSTOM_ID custom-ID field}.
   * 
   * @see #FIELD_CUSTOM_ID
   * 
   * @return the UID of this entry or <code>null</code> if NOT available.
   */
  Object getCustomId();

  /**
   * This method gets the value of the {@link #FIELD_TITLE title field}.
   * 
   * @see #FIELD_TITLE
   * 
   * @return the title of this entry.
   */
  String getTitle();

  /**
   * This method gets the author of this entry.
   * 
   * @see #FIELD_CREATOR
   * 
   * @return the author of this entry or <code>null</code> if NOT available.
   */
  String getCreator();

  /**
   * This method gets the value of the {@link #FIELD_SOURCE text field}.
   * 
   * @see #FIELD_TEXT
   * 
   * @return the text of this entry.
   */
  String getText();

  /**
   * This method gets the value of the {@link #FIELD_TYPE type field}.
   * 
   * @see #FIELD_TYPE
   * 
   * @return the type of this entry or <code>null</code> if NOT available.
   */
  String getType();

  /**
   * This method gets the value of the {@link #FIELD_SOURCE source field}.
   * 
   * @see #FIELD_SOURCE
   * 
   * @return the source of this entry or <code>null</code> if NOT available.
   */
  String getSource();

  /**
   * This method gets the size of the content.
   * 
   * @see #FIELD_SIZE
   * 
   * @return the size of this entry or <code>null</code> if NOT available.
   */
  Long getSize();

  /**
   * {@inheritDoc}
   * 
   * @see #FIELD_ID
   * 
   * @return the ID of this entry or <code>null</code> if the entry is transient and has not yet been added to
   *         the search-index (made persistent).
   */
  @Override
  Long getId();

}
