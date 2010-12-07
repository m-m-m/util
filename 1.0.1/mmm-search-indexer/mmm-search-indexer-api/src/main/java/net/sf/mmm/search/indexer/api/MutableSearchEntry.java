/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.api;

import net.sf.mmm.search.api.SearchEntry;

/**
 * This is the interface for an entry in the search index.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableSearchEntry extends SearchEntry {

  /**
   * This method sets the field with the given <code>name</code> to the given
   * <code>value</code>.<br/>
   * It is forbidden to change the value of {@link #FIELD_ID} and doing so will
   * cause an exception.<br/>
   * For the supported types see
   * {@link net.sf.mmm.search.api.config.SearchFieldType}. Additionally
   * {@link java.io.Reader} can be used to index large texts.<br/>
   * <b>ATTENTION:</b><br>
   * A <code>value</code> of the type {@link java.io.Reader} should only be used
   * when indexing really large texts. In this case the
   * {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getMode()
   * mode} has to be
   * {@link net.sf.mmm.search.api.config.SearchFieldMode#SEARCHABLE} and
   * {@link net.sf.mmm.search.api.config.SearchFieldConfiguration#getType()
   * type} has to be {@link net.sf.mmm.search.api.config.SearchFieldType#TEXT}
   * (NOT {@link java.io.Reader}). Further the {@link java.io.Reader} will only
   * be {@link java.io.Reader#close() closed} when the
   * {@link MutableSearchEntry} has been
   * {@link SearchIndexer#add(MutableSearchEntry) added} or
   * {@link SearchIndexer#update(MutableSearchEntry) updated}. If you never
   * write the {@link MutableSearchEntry} to the index for some reason, you are
   * responsible for closing the reader.
   * 
   * @param name is the name of the field. E.g. {@link #FIELD_TEXT}.
   * @param value is the value of the field to set.
   */
  void setField(String name, Object value);

  /**
   * This method sets the {@link #FIELD_URI URI}.
   * 
   * @see #setField(String, Object)
   * @see #getUri()
   * 
   * @param uri is the URI to set.
   */
  void setUri(String uri);

  /**
   * This method sets the {@link #FIELD_CUSTOM_ID UID}.
   * 
   * @see #setField(String, Object)
   * @see #getCustomId()
   * 
   * @param cid is the UID to set.
   */
  void setCustomId(Object cid);

  /**
   * This method sets the {@link #FIELD_TITLE title}.
   * 
   * @see #setField(String, Object)
   * @see #getTitle()
   * 
   * @param title is the title to set.
   */
  void setTitle(String title);

  /**
   * This method sets the {@link #FIELD_TEXT text}.
   * 
   * @see #setField(String, Object)
   * 
   * @param text is the text to set.
   */
  void setText(String text);

  /**
   * This method sets the {@link #FIELD_KEYWORDS keywords}.
   * 
   * @see #setField(String, Object)
   * 
   * @param keywords are the keywords to set.
   */
  void setKeywords(String keywords);

  /**
   * This method sets the {@link #FIELD_CREATOR creator}.
   * 
   * @see #setField(String, Object)
   * 
   * @param author is the author to set.
   */
  void setCreator(String author);

  /**
   * This method sets the {@link #FIELD_TYPE type}.
   * 
   * @see #setField(String, Object)
   * @see #getType()
   * 
   * @param type is the type to set.
   */
  void setType(String type);

  /**
   * This method sets the {@link #FIELD_SOURCE source}.
   * 
   * @see #setField(String, Object)
   * @see #getSource()
   * 
   * @param source is the source to set.
   */
  void setSource(String source);

  /**
   * This method sets the {@link #FIELD_SIZE size}.
   * 
   * @see #setField(String, Object)
   * @see #getSource()
   * 
   * @param size is the size to set.
   */
  void setSize(long size);

  /**
   * This method sets the boost of this this entry. The boost is a positive
   * value. The higher the boost, the
   * 
   * @param boost is the
   */
  void setBoost(double boost);

}
