/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the interface for the configuration of a
 * {@link net.sf.mmm.search.api.SearchEntry#getType() type} of a
 * {@link net.sf.mmm.search.api.SearchEntry}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEntryType extends AttributeReadId<String>, AttributeReadTitle<String> {

  /**
   * The {@link #getId() ID} of the {@link SearchEntryType} that represents any
   * type. In a search it can be used as wildcard to match all types.
   */
  String ID_ANY = SearchSource.ID_ANY;

  /**
   * The {@link #getId() ID} of the {@link SearchEntryType} that is the default
   * used as fallback for unknown types.
   * 
   * @see SearchEngineConfiguration#getEntryType(String)
   */
  String ID_DEFAULT = "default";

  /**
   * {@inheritDoc}
   * 
   * @return the ID of this {@link SearchEntryType}. It is typically the file
   *         extension in {@link String#toLowerCase() lower-case} excluding the
   *         dot (e.g. "doc", "htm", "html", etc.).
   */
  String getId();

  /**
   * This method gets the title of this {@link SearchEntryType}. It is the name
   * of this {@link SearchEntryType} for end-users in default language
   * (English).
   * 
   * @return the display title.
   */
  String getTitle();

  /**
   * This method gets the name (path relative to icon folder) of the icon
   * representing this {@link SearchEntryType}.
   * 
   * @return the name of the icon representing the filetype with the given
   *         <code>extension</code> or a default icon if no according icon could
   *         be found.
   */
  String getIcon();

}
