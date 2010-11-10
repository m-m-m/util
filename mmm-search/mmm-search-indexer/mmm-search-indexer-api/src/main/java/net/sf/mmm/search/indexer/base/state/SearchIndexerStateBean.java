/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.state;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.state.SearchIndexerSourceState;
import net.sf.mmm.search.indexer.api.state.SearchIndexerState;

/**
 * This is the implementation of {@link SearchIndexerState} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "search-indexer-state")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexerStateBean implements SearchIndexerState {

  /** @see #getIndexingDate() */
  @XmlElement(name = "indexing-date")
  private Date indexingDate;

  /** @see #getSources() */
  @XmlElementWrapper(name = "sources")
  @XmlElement(name = "source-state")
  private List<SearchIndexerSourceStateBean> sources;

  /** @see #getSourceStateMap() */
  private transient Map<String, SearchIndexerSourceStateBean> sourceStateMap;

  /** @see #getConfigurationLocation() */
  private transient String configurationLocation;

  /**
   * The constructor.
   */
  public SearchIndexerStateBean() {

    super();
  }

  /**
   * @return the sourceMap
   */
  protected Map<String, SearchIndexerSourceStateBean> getSourceStateMap() {

    if (this.sourceStateMap == null) {
      Map<String, SearchIndexerSourceStateBean> map = new HashMap<String, SearchIndexerSourceStateBean>();
      if (this.sources != null) {
        for (SearchIndexerSourceStateBean source : this.sources) {
          map.put(source.getSource(), source);
        }
      }
      this.sourceStateMap = map;
    }
    return this.sourceStateMap;
  }

  /**
   * This method gets the {@link List} of {@link SearchIndexerSourceStateBean}.<br/>
   * <b>ATTENTION:</b><br/>
   * Do NOT modify this list externally.
   * 
   * @see #getOrCreateSourceState(String)
   * 
   * @return the {@link List} of {@link SearchIndexerSourceStateBean}s.
   */
  public List<SearchIndexerSourceStateBean> getSources() {

    return this.sources;
  }

  /**
   * @param sources is the sources to set
   */
  public void setSources(List<SearchIndexerSourceStateBean> sources) {

    this.sources = sources;
    this.sources = null;
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexerSourceState getSourceState(String source) {

    return getSourceStateMap().get(source);
  }

  /**
   * @see #getSourceState(String)
   * 
   * @param source is the source-ID for which the state is requested.
   * @return the requested {@link SearchIndexerSourceState}. If it does not
   *         exist, it is created and associated.
   */
  public SearchIndexerSourceStateBean getOrCreateSourceState(String source) {

    SearchIndexerSourceStateBean sourceStatus = getSourceStateMap().get(source);
    if (sourceStatus == null) {
      sourceStatus = new SearchIndexerSourceStateBean();
      sourceStatus.setSource(source);
      if (this.sources == null) {
        this.sources = new ArrayList<SearchIndexerSourceStateBean>();
      }
      this.sources.add(sourceStatus);
      getSourceStateMap().put(source, sourceStatus);
    }
    return sourceStatus;
  }

  /**
   * This method gets the location URI where the configuration for this bean is
   * stored. This is a transient attribute dynamically added after loading.
   * 
   * @return the configurationLocation
   */
  public String getConfigurationLocation() {

    return this.configurationLocation;
  }

  /**
   * @param configurationLocation is the location to set
   */
  public void setConfigurationLocation(String configurationLocation) {

    this.configurationLocation = configurationLocation;
  }

  /**
   * {@inheritDoc}
   */
  public Date getIndexingDate() {

    return this.indexingDate;
  }

  /**
   * This method sets the {@link #getIndexingDate() indexing-date}.
   * 
   * @param indexingDate is the new indexing-date.
   */
  public void setIndexingDate(Date indexingDate) {

    this.indexingDate = indexingDate;
  }

}
