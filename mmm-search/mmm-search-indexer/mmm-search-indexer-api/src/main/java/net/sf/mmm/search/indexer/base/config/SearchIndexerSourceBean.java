/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import net.sf.mmm.search.base.config.SearchSourceBean;
import net.sf.mmm.search.indexer.api.config.SearchIndexerSource;

/**
 * This is the implementation of {@link SearchIndexerSource} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchIndexerSourceBean extends SearchSourceBean implements SearchIndexerSource {

  /** @see #getUpdateStrategy() */
  @XmlAttribute(name = "update-strategy")
  private String updateStrategy;

  /** @see #getLocations() */
  @XmlElementWrapper(name = "locations")
  @XmlElement(name = "location")
  private List<SearchIndexerDataLocationBean> locations;

  /**
   * The constructor.
   */
  public SearchIndexerSourceBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getUpdateStrategy() {

    if (this.updateStrategy == null) {
      return UPDATE_STRATEGY_LAST_MODIFIED;
    }
    return this.updateStrategy;
  }

  /**
   * This method sets the {@link #getUpdateStrategy() update-strategy}.
   * 
   * @param updateStrategy is the new value to set.
   */
  public void setUpdateStrategy(String updateStrategy) {

    this.updateStrategy = updateStrategy;
  }

  /**
   * {@inheritDoc}
   */
  public List<SearchIndexerDataLocationBean> getLocations() {

    if (this.locations == null) {
      this.locations = new ArrayList<SearchIndexerDataLocationBean>();
    }
    return this.locations;
  }

  /**
   * @param locations is the {@link List} of locations to set
   */
  public void setLocations(List<SearchIndexerDataLocationBean> locations) {

    this.locations = locations;
  }

}
