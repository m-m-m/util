/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.base;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.search.engine.api.config.SearchEntryType;
import net.sf.mmm.search.engine.base.config.SearchEntryTypeBean;
import net.sf.mmm.search.view.api.SearchEntryTypeView;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the implementation of the {@link SearchEntryTypeView} interface as
 * java bean.
 * 
 * @see #combine(SearchEntryType, long)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchEntryTypeViewBean extends SearchEntryTypeBean implements SearchEntryTypeView {

  /** The {@link Logger}. */
  private static final Logger LOGGER = LoggerFactory.getLogger(SearchEntryTypeViewBean.class);

  /** @see #getCombinedIds() */
  private final Set<String> combinedIds;

  /** @see #getEntryCount() */
  private long entryCount;

  /**
   * The constructor.
   */
  public SearchEntryTypeViewBean() {

    super();
    this.combinedIds = new HashSet<String>();
  }

  /**
   * This method combines the given <code>type</code> with this object.
   * Therefore the {@link SearchEntryType#getTitle() title} of all
   * {@link #combine(SearchEntryType, long) combined} types has to be equal.
   * 
   * @param type is the {@link SearchEntryType} to combine.
   * @param typeCount is the
   *        {@link net.sf.mmm.search.engine.api.SearchEngine#count(String, String)
   *        count} for the given <code>type</code>.
   */
  public void combine(SearchEntryType type, long typeCount) {

    NlsNullPointerException.checkNotNull(SearchEntryType.class, type);
    if (type instanceof SearchEntryTypeView) {
      // internal error in code
      throw new NlsIllegalStateException();
    }
    String typeTitle = type.getTitle();
    String title = getTitle();
    if (title == null) {
      setTitle(typeTitle);
    } else if (!title.equals(typeTitle)) {
      throw new NlsIllegalStateException();
    }
    String typeIcon = type.getIcon();
    String id = getId();
    String typeId = type.getId();
    if (typeIcon != null) {
      if (id == null) {
        setId(typeId);
        setIcon(typeIcon);
      } else {
        if (!typeIcon.equals(getIcon())) {
          // throw new NlsIllegalStateException();
          LOGGER.warn("Mismatching icons for " + SearchEntryType.class.getSimpleName()
              + " with title '" + title + "' (" + typeIcon + " != " + getIcon() + ")");
        }
      }
    }
    this.entryCount = this.entryCount + typeCount;
    if (!ID_ANY.equals(typeId)) {
      this.combinedIds.add(typeId);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Collection<String> getCombinedIds() {

    return this.combinedIds;
  }

  /**
   * {@inheritDoc}
   */
  public long getEntryCount() {

    return this.entryCount;
  }

}
