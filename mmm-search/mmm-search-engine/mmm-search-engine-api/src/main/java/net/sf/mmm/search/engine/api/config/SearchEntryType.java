/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.api.config;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.util.lang.api.attribute.AttributeReadId;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the interface for the configuration of a {@link net.sf.mmm.search.api.SearchEntry#getType() type}
 * of a {@link net.sf.mmm.search.api.SearchEntry}. It represents a content-type for the view.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchEntryType extends AttributeReadId<String>, AttributeReadTitle<String> {

  /**
   * The {@link #getId() ID} of the {@link SearchEntryType} that represents any type. In a search it can be
   * used as wildcard to match all types. For the view this {@link SearchEntryType} also defines the default
   * {@link #getIcon() icon} used as fallback for unknown types.
   */
  String ID_ANY = SearchSource.ID_ANY;

  /**
   * {@inheritDoc}
   * 
   * @return the ID of this {@link SearchEntryType}. It is typically the file extension in
   *         {@link String#toLowerCase() lower-case} excluding the dot (e.g. "doc", "html", etc.).
   */
  @Override
  String getId();

  /**
   * This method gets the title of this {@link SearchEntryType}. It is the name of this
   * {@link SearchEntryType} for end-users in default language (English).
   * 
   * @return the display title.
   */
  @Override
  String getTitle();

  /**
   * This method gets the name (path relative to icon folder) or URL of the icon representing this
   * {@link SearchEntryType}.
   * 
   * @return the name of the icon representing the file-type or a default icon if no according icon could be
   *         found. May be <code>null</code> for {@link net.sf.mmm.search.view.api.SearchEntryTypeView
   *         combined} types.
   */
  String getIcon();

}
