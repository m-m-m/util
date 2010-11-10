/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.base.config.SearchConfigurationBean;
import net.sf.mmm.search.engine.api.config.SearchEngineConfiguration;
import net.sf.mmm.search.engine.api.config.SearchEngineProperties;
import net.sf.mmm.search.engine.api.config.SearchEntryType;

/**
 * This is the implementation of {@link SearchEngineConfiguration} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "search")
public class SearchEngineConfigurationBean extends SearchConfigurationBean implements
    SearchEngineConfiguration {

  /** @see #getSearchProperties() */
  @XmlElement(name = "search-properties")
  private SearchEnginePropertiesBean searchProperties;

  /** @see #getEntryTypes() */
  @XmlElementWrapper(name = "entry-types")
  @XmlElement(name = "type")
  private List<SearchEntryTypeBean> entryTypes;

  /** @see #getEntryType(String) */
  private transient Map<String, SearchEntryTypeBean> entryTypeMap;

  /**
   * The constructor.
   */
  public SearchEngineConfigurationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public SearchEntryType getEntryType(String id) {

    SearchEntryType result = getEntryTypeMap().get(id);
    if (result == null) {
      result = getEntryTypeMap().get(SearchEntryType.ID_DEFAULT);
    }
    return result;
  }

  /**
   * This method gets the {@link Map} of {@link SearchEntryTypeBean}s.
   * 
   * @return the entry-type-map.
   */
  protected Map<String, SearchEntryTypeBean> getEntryTypeMap() {

    if (this.entryTypeMap == null) {
      if (this.entryTypes == null) {
        return Collections.emptyMap();
      }
      Map<String, SearchEntryTypeBean> map = new HashMap<String, SearchEntryTypeBean>();
      for (SearchEntryTypeBean type : this.entryTypes) {
        map.put(type.getId(), type);
      }
      this.entryTypeMap = map;
    }
    return this.entryTypeMap;
  }

  /**
   * {@inheritDoc}
   */
  public List<SearchEntryTypeBean> getEntryTypes() {

    if (this.entryTypes == null) {
      return Collections.emptyList();
    }
    return this.entryTypes;
  }

  /**
   * @param entryTypes is the entryTypes to set
   */
  public void setEntryTypes(List<SearchEntryTypeBean> entryTypes) {

    this.entryTypes = entryTypes;
    this.entryTypeMap = null;
  }

  /**
   * {@inheritDoc}
   */
  public SearchEngineProperties getSearchProperties() {

    if (this.searchProperties == null) {
      return new SearchEnginePropertiesBean();
    }
    return this.searchProperties;
  }

  /**
   * @param searchProperties is the searchProperties to set
   */
  public void setSearchProperties(SearchEnginePropertiesBean searchProperties) {

    this.searchProperties = searchProperties;
  }

}
