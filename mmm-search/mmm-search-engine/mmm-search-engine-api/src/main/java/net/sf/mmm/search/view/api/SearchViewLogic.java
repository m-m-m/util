/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.view.api;

import java.util.Collection;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEntryType;
import net.sf.mmm.util.component.api.Refreshable;
import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.nls.api.NlsLocalizer;
import net.sf.mmm.util.xml.api.XmlUtil;

/**
 * This is the interface to access components and logic of the search.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface SearchViewLogic extends Refreshable {

  /**
   * This method gets the {@link SearchEngineConfiguration}.
   * 
   * @see #getEntryTypeViews()
   * @see #getEntryType(String)
   * @see #getSourceViews()
   * 
   * @return the {@link SearchEngineConfiguration}.
   */
  SearchEngineConfiguration getConfiguration();

  /**
   * This method gets the list of {@link SearchEntryTypeView}s sorted by {@link SearchEntryType#getTitle()
   * title}.
   * 
   * @return the {@link SearchEntryTypeView}s.
   */
  Collection<? extends SearchEntryTypeView> getEntryTypeViews();

  /**
   * This method gets the list of views on the {@link SearchSourceView}s sorted by
   * {@link SearchSourceView#getTitle() title}.
   * 
   * @return the sources.
   */
  Collection<? extends SearchSourceView> getSourceViews();

  /**
   * This method gets the {@link SearchEntryTypeView} or {@link SearchEntryType} for the given <code>id</code>
   * .
   * 
   * @param id is the {@link SearchEntryType#getId() ID} of the requested {@link SearchEntryType}.
   * @return the requested {@link SearchEntryType}.
   */
  SearchEntryType getEntryType(String id);

  /**
   * This method gets the {@link ManagedSearchEngine}.
   * 
   * @return the {@link ManagedSearchEngine}.
   */
  ManagedSearchEngine getSearchEngine();

  /**
   * This method gets the {@link XmlUtil} that can be used for {@link XmlUtil#escapeXml(String, boolean)
   * escaping} to prevent from XSS-attacks.
   * 
   * @return the {@link XmlUtil}.
   */
  XmlUtil getXmlUtil();

  /**
   * This method gets the {@link Iso8601Util} that can be used to format and parse dates.
   * 
   * @return the {@link Iso8601Util}.
   */
  Iso8601Util getIso8601Util();

  /**
   * This method gets the {@link NlsLocalizer} used for localization of the UI.
   * 
   * @return the {@link NlsLocalizer}.
   */
  NlsLocalizer getNlsLocalizer();

  /**
   * This method gets the date of the last refresh as String.
   * 
   * @return the last refresh date.
   */
  String getLastRefreshDate();

  /**
   * This method reloads the {@link #getConfiguration() configuration} and the
   * {@link net.sf.mmm.search.engine.api.SearchEngine} on the fly.<br/>
   * <b>ATTENTION:</b><br>
   * The search-engine will not be rebuild on reload so configuration-changes only reflect the view and NOT
   * the {@link #getSearchEngine() search-engine}.
   * 
   * {@inheritDoc}
   */
  @Override
  boolean refresh();

  /**
   * This method get the display-title from the given <code>searchEntry</code>. In advance to
   * {@link SearchEntry#getTitle()} this method will create a combination of filename and the actual title and
   * handle <code>null</code> values.
   * 
   * @param searchEntry is the {@link SearchEntry} where to get the {@link SearchEntry#getTitle() title} from.
   * 
   * @return the display {@link SearchEntry#getTitle() title} of the {@link SearchEntry}.
   */
  String getDisplayTitle(SearchEntry searchEntry);

}
