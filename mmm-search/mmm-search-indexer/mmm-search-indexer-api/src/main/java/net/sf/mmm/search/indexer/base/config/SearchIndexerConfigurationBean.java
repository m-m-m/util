/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.base.config.SearchConfigurationBean;
import net.sf.mmm.search.indexer.api.config.SearchIndexerConfiguration;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.transformer.base.StringTransformerChain;

/**
 * This is the implementation of {@link SearchIndexerConfiguration} as
 * JAXB-ready Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "search")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexerConfigurationBean extends SearchConfigurationBean implements
    SearchIndexerConfiguration {

  /** @see #getFilters() */
  @XmlElementWrapper(name = "filters")
  @XmlElement(name = "filter-chain")
  private List<FilterRuleChain<String>> filters;

  /** @see #getTransformers() */
  @XmlElementWrapper(name = "uri-rewriters")
  @XmlElement(name = "transformer-chain")
  private List<StringTransformerChain> transformers;

  /** @see #getLocations() */
  @XmlElementWrapper(name = "locations")
  @XmlElement(name = "location")
  private List<SearchIndexDataLocationBean> locations;

  /**
   * The constructor.
   */
  public SearchIndexerConfigurationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public List<SearchIndexDataLocationBean> getLocations() {

    return this.locations;
  }

  /**
   * @param locations is the {@link List} of locations to set
   */
  public void setLocations(List<SearchIndexDataLocationBean> locations) {

    this.locations = locations;
  }

  /**
   * @return the transformers
   */
  public List<StringTransformerChain> getTransformers() {

    return this.transformers;
  }

  /**
   * @param transformers is the transformers to set
   */
  public void setTransformers(List<StringTransformerChain> transformers) {

    this.transformers = transformers;
  }

  /**
   * This method gets the {@link List} of
   * {@link net.sf.mmm.util.filter.api.Filter}s that
   * {@link net.sf.mmm.util.filter.api.Filter#accept(Object) decide} which
   * resources should be indexed.
   * 
   * @see net.sf.mmm.search.indexer.api.config.SearchIndexDataLocation#getFilter()
   * 
   * @return the {@link Collection} with the {@link FilterRuleChain}s.
   */
  public Collection<FilterRuleChain<String>> getFilters() {

    return this.filters;
  }

  /**
   * This method sets the {@link #getFilters() filters}.
   * 
   * @param filters is the list of {@link FilterRuleChain}.
   */
  public void setFilters(List<FilterRuleChain<String>> filters) {

    this.filters = filters;
  }

}
