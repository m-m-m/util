/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.indexer.api.config.SearchConfiguration;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.transformer.base.StringTransformerChain;

/**
 * This is the implementation of {@link SearchConfiguration} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchConfigurationBean implements SearchConfiguration {

  @XmlElementWrapper(name = "filters")
  @XmlElement(name = "filter-chain")
  private List<FilterRuleChain<String>> filters;

  /** @see #getTransformers() */
  @XmlElementWrapper(name = "uri-rewriters")
  @XmlElement(name = "transformer-chain")
  private List<StringTransformerChain> transformers;

  /** @see #getDirectories() */
  @XmlElementWrapper(name = "directories")
  @XmlElement(name = "directory")
  private List<SearchDirectoryBean> directories;

  /** @see #getSources() */
  @XmlElementWrapper(name = "sources")
  @XmlElement(name = "source")
  private List<SearchSourceBean> sources;

  /**
   * The constructor.
   */
  public SearchConfigurationBean() {

    super();
  }

  /**
   * @return the sources
   */
  public List<SearchSourceBean> getSources() {

    return this.sources;
  }

  /**
   * @param sources is the sources to set
   */
  public void setSources(List<SearchSourceBean> sources) {

    this.sources = sources;
  }

  /**
   * {@inheritDoc}
   */
  public List<SearchDirectoryBean> getDirectories() {

    return this.directories;
  }

  /**
   * @param directories is the directories to set
   */
  public void setDirectories(List<SearchDirectoryBean> directories) {

    this.directories = directories;
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
   * TODO: javadoc
   * 
   * @param filters
   */
  public void setFilters(List<FilterRuleChain<String>> filters) {

    this.filters = filters;
  }
}
